FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .

RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final, lightweight runtime image
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar Ansari-shop-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "Ansari-shop-0.0.1-SNAPSHOT.jar"]