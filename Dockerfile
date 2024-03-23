FROM ubuntu:latest AS build
RUN apt update
RUN apt install openjdk-17-jdk -y
COPY . .
RUN ./gradlew bootJar --no-deamon

FROM openjdk:17-jdk-slim
EXPOSE 8080
COPY --from=build /build/libs/educonnect-1.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]