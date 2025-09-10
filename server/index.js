const express = require("express");
const cors = require("cors");
const dotenv = require("dotenv");
const axios = require("axios");
const mysql = require("mysql2/promise");

dotenv.config();

const app = express();
app.use(cors());
app.use(express.json());

// --- MySQL Connection Pool ---
const pool = mysql.createPool({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
  waitForConnections: true,
  connectionLimit: 10,
  queueLimit: 0,
});

// --- Schema Description Loading ---
let mysqlSchemaDescription = "";

async function loadSchemaDescription() {
  mysqlSchemaDescription = "MySQL Schema:\n";

  try {
    console.log("⏳ Loading MySQL schema...");
    const [tables] = await pool.query("SHOW TABLES");
    const tableNames = tables.map((t) => Object.values(t)[0]);

    for (const table of tableNames) {
      const [columns] = await pool.query(`SHOW COLUMNS FROM \`${table}\``);
      const colDescriptions = columns
        .map((col) => `${col.Field} (${col.Type})`)
        .join(", ");
      mysqlSchemaDescription += `  Table "${table}": ${colDescriptions}\n`;
    }
    console.log("✅ MySQL schema loaded.");
  } catch (error) {
    console.error("❌ Error loading MySQL schema:", error.message);
    mysqlSchemaDescription += "  Error loading schema.\n";
  }

  console.log("📋 MySQL Schema loaded:\n", mysqlSchemaDescription);
}

setTimeout(loadSchemaDescription, 5000);

// --- API Endpoint ---
app.post("/api/chat", async (req, res) => {
  const { message } = req.body;

  if (!message) {
    return res.status(400).json({ error: "Missing 'message' in request body." });
  }

  if (!mysqlSchemaDescription) {
    console.warn("⚠️ Schema description not loaded yet. Retrying load...");
    await loadSchemaDescription();
    if (!mysqlSchemaDescription) {
      return res.status(503).json({ error: "Schema description is not available yet. Please try again later." });
    }
  }

  try {
    // STEP 1: Use Gemini to generate SQL query
    console.log(`[${new Date().toISOString()}] Received question: "${message}"`);
    const queryGenPrompt = `
Dưới đây là schema cơ sở dữ liệu MySQL:

${mysqlSchemaDescription}

Câu hỏi của người dùng: "${message}"

Nhiệm vụ:
1. Tạo câu lệnh SQL hợp lệ để trả lời câu hỏi.
2. Trả về KẾT QUẢ CHỈ LÀ MỘT ĐỐI TƯỢNG JSON DUY NHẤT, không có giải thích hay định dạng markdown nào khác.
   - JSON phải có dạng: \`{"database": "mysql", "query": "SELECT ..."}\`

Ví dụ JSON cho MySQL: \`{"database": "mysql", "query": "SELECT name FROM products WHERE category = 'Electronics'"}\`

JSON Output:`;

    console.log("⏳ Calling Gemini for query generation...");
    const queryGenRes = await axios.post(
      `https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=${process.env.GEMINI_API_KEY}`,
      {
        contents: [{ role: "user", parts: [{ text: queryGenPrompt }] }],
        generationConfig: { responseMimeType: "application/json" }
      },
      { headers: { 'Content-Type': 'application/json' } }
    );

    // Safely parse the Gemini response
    let queryInfo;
    try {
      const rawText = queryGenRes.data?.candidates?.[0]?.content?.parts?.[0]?.text || "";
      console.log("Raw Gemini Query Gen Response:", rawText);
      const cleanedJsonString = rawText.replace(/```json|```/g, "").trim();
      queryInfo = JSON.parse(cleanedJsonString);
      console.log("Parsed Query Info:", queryInfo);

      if (!queryInfo || !queryInfo.database || !queryInfo.query) {
        throw new Error("Invalid JSON structure from Gemini.");
      }
      if (queryInfo.database !== "mysql") {
        throw new Error("Only MySQL queries are supported in this version.");
      }
    } catch (parseError) {
      console.error("❌ Error parsing Gemini query generation response:", parseError.message);
      console.error("Raw response was:", queryGenRes.data?.candidates?.[0]?.content?.parts?.[0]?.text);
      return res.status(500).json({ error: "Lỗi xử lý phản hồi từ AI để tạo truy vấn." });
    }

    // STEP 2: Execute the SQL query
    let results;
    let executedQuery = queryInfo.query.replace(/`/g, '');

    console.log(`⏳ Executing MySQL query...`);
    console.log("SQL Query:", executedQuery);
    const [rows] = await pool.query(executedQuery);
    results = rows;
    console.log(`✅ MySQL query executed. Found ${results.length} rows.`);

    // STEP 3: Generate natural language answer
    console.log("⏳ Calling Gemini for natural language answer...");
    const resultPrompt = `
Câu hỏi gốc của người dùng: "${message}"
Câu truy vấn đã thực thi: ${executedQuery}
Kết quả (${results.length} bản ghi): ${JSON.stringify(results)}

👉 Dựa vào câu hỏi và kết quả truy vấn, hãy viết một câu trả lời bằng tiếng Việt tự nhiên, thân thiện cho người dùng.
   - KHÔNG hiển thị dữ liệu dạng JSON thô.
   - KHÔNG đề cập đến cú pháp SQL.
   - Trình bày kết quả một cách rõ ràng, dễ hiểu. Nếu không có kết quả, hãy thông báo như vậy.

Câu trả lời tự nhiên:`;

    const replyRes = await axios.post(
      `https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=${process.env.GEMINI_API_KEY}`,
      {
        contents: [{ role: "user", parts: [{ text: resultPrompt }] }],
      },
      { headers: { 'Content-Type': 'application/json' } }
    );

    const answer =
      replyRes.data?.candidates?.[0]?.content?.parts?.[0]?.text ||
      "Xin lỗi, tôi không thể tạo câu trả lời tự nhiên vào lúc này.";

    console.log("✅ Natural language answer generated."+ " Answer: "+answer);
    res.json({
      database: "mysql",
      query: executedQuery,
      result: results,
      answer: answer.trim(),
    });
  } catch (error) {
    console.error("❌ Error in /api/chat:", error.response ? JSON.stringify(error.response.data) : error.message);
    if (error.response && error.response.data && error.response.data.error) {
      console.error("Gemini API Error:", error.response.data.error.message);
      res.status(500).json({ error: `Lỗi từ AI: ${error.response.data.error.message}` });
    } else if (error.code) {
      res.status(500).json({ error: `Lỗi cơ sở dữ liệu: ${error.message} (Code: ${error.code})` });
    } else {
      res.status(500).json({ error: "Đã xảy ra lỗi không mong muốn trong quá trình xử lý yêu cầu." });
    }
  }
});

// --- Server Start ---
const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`🚀 Server running on http://localhost:${PORT}`);
  console.log(`Connect to MySQL at: ${process.env.DB_HOST}`);
});