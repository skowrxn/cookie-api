FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar cookie-app-backend-1.0-beta.jar
ENTRYPOINT ["java", "-jar", "cookie-app-backend-1.0-beta.jar"]