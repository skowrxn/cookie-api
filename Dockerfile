FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/*.jar cookie-app-backend-1.0-beta.jar
ENTRYPOINT ["java", "-jar", "cookie-app-backend-1.0-beta.jar"]
