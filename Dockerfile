# Use a full JDK image because this first stage needs to compile/build the Java application.
FROM eclipse-temurin:21-jdk AS build

# Set the working directory inside the Docker image to /app.
WORKDIR /app

# Copy the Maven wrapper script from your project into the Docker image.
COPY mvnw .

# Copy the Maven wrapper configuration folder into the Docker image.
COPY .mvn .mvn

# Copy the Maven project file first so Docker can cache dependency downloads when source code changes.
COPY pom.xml .

# Copy the application source code into the Docker image.
COPY src src

# Give the Maven wrapper execute permission inside the Linux-based Docker image.
RUN chmod +x mvnw

# Build the Spring Boot application inside Docker and skip tests during the image build.
RUN ./mvnw clean package -DskipTests


# Start the final runtime image using a smaller JRE image because running the app does not require the full JDK.
FROM eclipse-temurin:21-jre

# Set the working directory inside the final runtime container.
WORKDIR /app

# Copy the built JAR file from the build stage into the final runtime image.
COPY --from=build /app/target/*.jar inventory_service.jar

# Document that this application listens on port 8081 inside the container.
EXPOSE 8082

# Start the Spring Boot application when the container runs.
ENTRYPOINT ["java", "-jar", "inventory_service.jar"]