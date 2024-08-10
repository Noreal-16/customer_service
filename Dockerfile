FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/client_person_service-0.0.1-SNAPSHOT.jar client_person_service.jar
EXPOSE 8081
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "client_person_service.jar"]