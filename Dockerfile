FROM gradle:8.6-jdk17 AS build

COPY . .

RUN ./gradlew build --no-daemon

RUN ./gradlew bootjar --no-daemon

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /build/libs/edu-connect-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]