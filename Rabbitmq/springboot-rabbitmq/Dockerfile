# Use a base image with Java
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app


ENV TZ=UTC
ENV DB_IP=localhost
ENV DB_PORT=3309
ENV DB_USER=root
ENV DB_PASSWORD=local-server1
ENV DB_DBNAME=energy-db


# Copy the built JAR file from the host machine into the container
COPY target/springboot-rabbitmq-0.0.1-SNAPSHOT.jar /app/app.jar

# Run the JAR file with Java
ENTRYPOINT ["java", "-jar", "app.jar"]
