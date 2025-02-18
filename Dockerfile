# First stage: Build the application
FROM openjdk:21-jdk AS builder
WORKDIR /app
COPY gradle/ gradle/
COPY build.gradle settings.gradle gradlew ./
RUN ./gradlew dependencies
COPY src ./src
RUN ./gradlew clean build

# Second stage: Run the application
FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/Task-0.0.1-SNAPSHOT.jar testapp.jar
EXPOSE 9092
ENTRYPOINT ["java", "-jar", "testapp.jar"]