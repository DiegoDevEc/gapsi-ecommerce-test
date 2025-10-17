# Etapa 1: Build con Maven
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Descargar dependencias (se cachea si pom.xml no cambia)
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar la aplicación
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen de producción
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar el JAR construido
COPY --from=builder /app/target/*.jar app.jar

# Copiar el archivo bd.json (base de datos JSON)
COPY bd.json /app/bd.json

# Exponer puerto 8080
EXPOSE 8080

# Variables de entorno (opcionales, pueden sobreescribirse en docker-compose)
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]