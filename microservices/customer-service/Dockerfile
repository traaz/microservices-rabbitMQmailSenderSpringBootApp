FROM openjdk:17
WORKDIR /app
COPY target/customer-service-0.0.1-SNAPSHOT.jar customer-service
ENTRYPOINT ["java", "-jar", "customer-service"]