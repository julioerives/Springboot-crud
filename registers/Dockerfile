FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/registers-0.0.1-SNAPSHOT.jar registers.jar

EXPOSE 3000

ENTRYPOINT ["java", "-jar", "registers.jar"]
