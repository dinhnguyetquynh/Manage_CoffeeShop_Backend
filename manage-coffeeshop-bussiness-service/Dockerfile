FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/manage-coffeeshop-bussiness-service-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/docker/wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
CMD ["./wait-for-it.sh", "dataservice:8080", "--", "./wait-for-it.sh", "redis:6379", "--", "java", "-jar", "app.jar"]
