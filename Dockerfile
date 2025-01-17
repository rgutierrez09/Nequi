FROM openjdk:17-jdk
WORKDIR /app
COPY application/app-service/build/libs/app-service.jar app.jar
COPY .env .env
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]