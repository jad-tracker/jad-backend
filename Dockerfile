FROM eclipse-temurin:21.0.5_11-jdk-jammy AS builder
WORKDIR /opt/app

COPY .mvn/ .mvn
COPY mvnw ./
COPY pom.xml ./
RUN ./mvnw dependency:go-offline

COPY ./src/ ./src/
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:21.0.5_11-jdk-jammy AS final
WORKDIR /opt/app

COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]