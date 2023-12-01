# Starting with this linux server
FROM maven:3.9-eclipse-temurin-21

# Create a directory called /app
# Go into the directory cd /app
WORKDIR /app

# Everything after this is in /app
# Copy file can put .
# Copy folder put folder name
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

# Build the application
RUN mvn package -Dmaven.test.skip=true

# Run the application
# Define environment variables
ENV PORT=5000
ENV SPRING_REDIS_HOST=localhost
ENV SPRING_REDIS_PORT=6379
ENV SPRING_REDIS_USERNAME=NOT_SET
ENV SPRING_REDIS_PASSWORD=NOT_SET

# Expose the port
EXPOSE ${PORT}

# Run the program
# Cloud providers use PORT instead of SERVER_PORT that is used in SpringBoot
ENTRYPOINT SERVER_PORT=${PORT} java -jar target/day15-0.0.1-SNAPSHOT.jar