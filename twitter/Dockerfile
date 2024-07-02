FROM openjdk:17-jdk-slim

# Install curl and other necessary packages
RUN apt-get update && apt-get install -y curl unzip zip && rm -rf /var/lib/apt/lists/*

# Install SDKMAN and Groovy
RUN curl -s "https://get.sdkman.io" | bash && \
    bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install groovy"

WORKDIR /app

COPY gradlew .
COPY gradle /app/gradle
COPY build.gradle settings.gradle /app/
COPY src /app/src

RUN chmod +x ./gradlew

RUN ./gradlew build

EXPOSE 8080

CMD ["java", "-jar", "build/libs/twitter-0.0.1-SNAPSHOT.jar"]
