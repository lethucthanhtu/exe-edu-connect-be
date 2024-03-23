FROM ubuntu:latest AS build
RUN apt update
RUN apt install openjdk-17-jdk -y
COPY . .
EXPOSE 8080
RUN ./gradlew bootrun
# RUN ./gradlew bootJar --no-deamon

# FROM openjdk:17-jdk-slim
# EXPOSE 8080
# COPY --from=build /build/libs/exe-edu-connect-be-0.0.1-SNAPSHOT-plain.jar exe-edu-connect-be-0.0.1-SNAPSHOT.jar

# ENTRYPOINT [ "java", "-jar", "exe-edu-connect-be-0.0.1-SNAPSHOT.jar" ]