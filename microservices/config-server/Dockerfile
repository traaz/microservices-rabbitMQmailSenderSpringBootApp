FROM openjdk:17
WORKDIR /app
COPY target/config-server-0.0.1-SNAPSHOT.jar config-server-service
ENTRYPOINT ["java", "-jar", "config-server-service"]