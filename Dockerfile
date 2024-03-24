FROM adoptium/openjdk:17-jdk-slim

WORKDIR /app

COPY gradle/wrapper /gradle/wrapper

COPY . .

RUN /gradle/wrapper gradle build

COPY build/libs/edu-connect-1.jar app.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
