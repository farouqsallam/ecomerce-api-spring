# --- Stage 1: The Builder ---
# We download a specific Maven version to build the app inside the container.
# "eclipse-temurin" is just the name of the Java vendor.
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy your project files into the container
COPY pom.xml .
COPY src ./src

# Build the .jar file (Skipping tests to make it faster)
RUN mvn clean package -DskipTests

# --- Stage 2: The Runner ---
# We switch to a tiny, lightweight Java Runtime (JRE) to run the app.
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# We pick the .jar file from the "build" stage above
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Command to start the app
ENTRYPOINT ["java", "-jar", "app.jar"]