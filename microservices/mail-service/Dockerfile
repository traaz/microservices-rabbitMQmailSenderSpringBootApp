FROM openjdk:17
WORKDIR /app
COPY target/mail-service-0.0.1-SNAPSHOT.jar mail-service
ENTRYPOINT ["java", "-jar", "mail-service"]