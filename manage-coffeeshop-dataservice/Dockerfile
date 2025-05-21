FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/manage-coffeeshop-dataservice-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/docker/wait-for-it.sh wait-for-it.sh

RUN chmod +x wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "mariadb:3306", "--", "java", "-jar", "app.jar"]