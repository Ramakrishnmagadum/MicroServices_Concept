FROM eclipse-temurin:8-jdk-alpine

WORKDIR /app

COPY /target/student-app.jar student-app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","student-app.jar"]
