# First stage: Build the application
FROM openjdk:21-jdk AS builder

# Set the working directory
WORKDIR /app
#"C:\Users\user\Downloads\task.pem"
# Copy only the Gradle wrapper and build files
COPY gradle/ gradle/
COPY build.gradle settings.gradle gradlew ./

# Download dependencies
RUN ./gradlew dependencies

# Copy the source code
COPY src ./src

# Build the application
RUN ./gradlew clean build

# Second stage: Run the application
FROM openjdk:21-jdk
WORKDIR /app

# Copy the built JAR from the previous stage
COPY build/libs/Task-0.0.1-SNAPSHOT.jar testapp.jar

# Expose port
EXPOSE 9092

# Run the application
ENTRYPOINT ["java", "-jar", "testapp.jar"]
