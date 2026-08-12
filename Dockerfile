FROM maven:3.9.11-eclipse-temurin-25 AS build

WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn -q -DskipTests package \
    && mv target/publicador_alarmas-*.jar target/app.jar

FROM eclipse-temurin:25-jre-jammy

WORKDIR /app

COPY --from=build /build/target/app.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
