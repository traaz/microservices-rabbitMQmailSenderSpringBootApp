FROM openjdk:17
WORKDIR /app
COPY target/eureka-server-0.0.1-SNAPSHOT.jar eureka-service
ENTRYPOINT ["java", "-jar", "eureka-service"]