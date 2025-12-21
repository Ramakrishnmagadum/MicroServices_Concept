FROM eclipse-temurin:8-jdk-alpine

WORKDIR /app

COPY /target/student-postgres-app.jar student-postgres-app.jar

EXPOSE 8078

ENTRYPOINT ["java","-jar","student-postgres-app.jar"]
