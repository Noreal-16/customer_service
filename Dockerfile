FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/client_person_service-0.0.1-SNAPSHOT.jar /app/customerservice.jar

COPY src/main/resources/schema.sql /app/schema.sql

ENTRYPOINT ["java", "-jar", "/app/customerservice.jar"]

EXPOSE 8081