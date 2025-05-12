package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.vn.zalopay.crypto.HMACUtil;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/business/payment/zalopay")
public class ZaloPayController {
    private final Map<String, String> config = new HashMap<String, String>() {{
        put("app_id", "554");
        put("key1", "8NdU5pG5R2spGHGhyO99HN1OhD8IQJBn");
        put("key2", "uUfsWgfLkRLzq6W2uNXTCxrfxs51auny");
        put("endpoint", "https://sb-openapi.zalopay.vn/v2/create");
    }};

    private String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }

    @PostMapping("/create-order")
    public String createOrder() {
        try {
            Random rand = new Random();
            int randomId = rand.nextInt(1000000);
//            api/business/payment/zalopay
            Map<String, Object> embedData = new HashMap<>();
            embedData.put("callback_url", "https://0e5d-171-252-153-14.ngrok-free.app/myapp/api/business/payment/zalopay/callback");



//            Map<String, Object>[] item = new HashMap[]{new HashMap<>()};
            List<Object> item = new ArrayList<>();

            Map<String, Object> order = new HashMap<String, Object>() {{
                put("app_id", config.get("app_id"));
                put("app_trans_id", getCurrentTimeString("yyMMdd") + "_" + randomId);
                put("app_time", System.currentTimeMillis());
                put("app_user", "user123");
                put("amount", 10000);
                put("description", "Lazada - Payment for the order #" + randomId);
                put("bank_code", "zalopayapp");
                put("item", new JSONArray(item).toString());
                put("embed_data", new JSONObject(embedData).toString());
            }};

            String data = order.get("app_id") + "|" + order.get("app_trans_id") + "|" + order.get("app_user") + "|"
                    + order.get("amount") + "|" + order.get("app_time") + "|" + order.get("embed_data") + "|"
                    + order.get("item");

            System.out.println("Data to sign: " + data);

            String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.get("key1"), data);
            order.put("mac", mac);
            System.out.println("Generated mac: " + mac);

            // Gửi request
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(config.get("endpoint"));

            System.out.println("THONG TIN GUI CHO ZALO:");
            List<NameValuePair> params = new ArrayList<>();
            for (Map.Entry<String, Object> e : order.entrySet()) {
                params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
                System.out.println(e.getKey() + " = " + e.getValue());
            }

            post.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = client.execute(post);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder resultJsonStr = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                resultJsonStr.append(line);
            }

            return resultJsonStr.toString(); // Trả JSON response từ Zalopay
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to create order.\"}";
        }
    }

    @PostMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestBody String body) {
        System.out.println("📥 ZaloPay callback received");

        JSONObject result = new JSONObject();

        try {
            JSONObject jsonData = new JSONObject(body);

            // Dữ liệu gốc từ ZaloPay
            String data = jsonData.getString("data");
            String reqMac = jsonData.getString("mac");

            // Tự tạo MAC từ dữ liệu để kiểm tra
            String calculatedMac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.get("key2"), data);

            // So sánh MAC để xác thực nguồn gốc callback
            if (!calculatedMac.equals(reqMac)) {
                System.out.println("❌ MAC mismatch. Callback is not valid.");
                result.put("return_code", -1);
                result.put("return_message", "mac not equal");
                return ResponseEntity.ok(result.toString());
            }

            // ✅ Nếu MAC hợp lệ: xử lý đơn hàng
            JSONObject dataObj = new JSONObject(data);
            String appTransId = dataObj.getString("app_trans_id");
            int amount = dataObj.getInt("amount");
            long zpTransId = dataObj.getLong("zp_trans_id");

            // TODO: Cập nhật trạng thái đơn hàng trong DB tại đây
            System.out.println("✅ Payment confirmed for order: " + appTransId + ", amount: " + amount + ", ZP TransId: " + zpTransId);

            result.put("return_code", 1);
            result.put("return_message", "OK");
            return ResponseEntity.ok(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            result.put("return_code", 0); // cho phép ZaloPay callback lại (tối đa 3 lần)
            result.put("return_message", e.getMessage());
            return ResponseEntity.ok(result.toString());
        }
    }

    @PostMapping("/query-order")
    public String queryOrderStatus(@RequestParam String appTransId) {
        try {
            String appId = config.get("app_id");
            String key1 = config.get("key1");

            // Tạo chuỗi data để ký
            String data = appId + "|" + appTransId + "|" + key1;
            String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, key1, data);

            // Chuẩn bị form data
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("app_id", appId));
            params.add(new BasicNameValuePair("app_trans_id", appTransId));
            params.add(new BasicNameValuePair("mac", mac));

            // Gửi POST request đến endpoint /v2/query
            String queryUrl = "https://sb-openapi.zalopay.vn/v2/query";
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(queryUrl);
            post.setEntity(new UrlEncodedFormEntity(params));

            // Đọc phản hồi
            CloseableHttpResponse response = client.execute(post);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder responseStr = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseStr.append(line);
            }

            client.close();
            return responseStr.toString(); // JSON string từ ZaloPay
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to query order status.\"}";
        }
    }

}

