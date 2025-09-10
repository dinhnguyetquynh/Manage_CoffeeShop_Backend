# Sử dụng image Node chính thức
FROM node:20-alpine

# Tạo thư mục làm việc trong container
WORKDIR /app

# Sao chép file package.json và package-lock.json (nếu có)
COPY package*.json ./

# Cài đặt các dependency
RUN npm install

# Sao chép toàn bộ mã nguồn vào container
COPY . .

# Expose cổng chạy Node server (ví dụ 3000)
EXPOSE 5000

# Lệnh chạy server
CMD ["node", "index.js"]
