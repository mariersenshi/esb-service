FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml

COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine

#directorio
WORKDIR /app

COPY target/*jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
