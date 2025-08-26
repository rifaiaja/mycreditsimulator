# syntax=docker/dockerfile:1.4

# ---------------------------
# Stage 1: Build with Maven
# ---------------------------
FROM maven:3.8.5-openjdk-17-slim AS build

WORKDIR /workspace

# copy only pom first to utilize layer cache for dependencies
COPY pom.xml ./
# if multi-module, copy settings and necessary module files
RUN mvn -B dependency:go-offline

# copy source then package
COPY src ./src
RUN mvn -B clean package -DskipTests

# ---------------------------
# Stage 2: Runtime
# ---------------------------
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the build result jar. If there is more than one jar, adjust the pattern or use the build result name.
COPY --from=build /workspace/target/*.jar ./app.jar

# port
EXPOSE 8080

# jalankan aplikasi
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
