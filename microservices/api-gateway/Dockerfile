FROM openjdk:17
WORKDIR /app
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway-service
ENTRYPOINT ["java", "-jar", "api-gateway-service"]