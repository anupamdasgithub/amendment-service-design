# Build stage
FROM registry.access.redhat.com/ubi9/openjdk-21:latest AS build
USER root
WORKDIR /build
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests

# Runtime stage
FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:latest
WORKDIR /deployments
COPY --from=build /build/target/amendments-service-*.jar app.jar
EXPOSE 8080
USER 185
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
