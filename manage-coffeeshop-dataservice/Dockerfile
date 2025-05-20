# manage-coffeeshop-dataservice/Dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/manage-coffeeshop-dataservice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
