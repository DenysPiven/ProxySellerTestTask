# Use the official OpenJDK 22 as a base image
FROM openjdk:22-jdk-slim

# Install dependencies
RUN apt-get update && apt-get install -y curl unzip zip && rm -rf /var/lib/apt/lists/*

# Install SDKMAN
RUN curl -s "https://get.sdkman.io" | bash && \
    bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install groovy"

# Set the working directory
WORKDIR /app

# Copy necessary files
COPY gradlew .
COPY gradle /app/gradle
COPY build.gradle settings.gradle /app/
COPY src /app/src

# Grant execute permissions for the gradlew script
RUN chmod +x ./gradlew

# Build the application
RUN ./gradlew build

# Expose the application port
EXPOSE 8080

# Set the entry point for the container
CMD ["java", "-jar", "build/libs/twitter-0.0.1-SNAPSHOT.jar"]
