# Etapa 1: Construcción (Build)
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copiamos los archivos de Maven (el wrapper y el pom)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Descargamos las dependencias (esto se cachea si no cambia el pom.xml)
RUN ./mvnw dependency:go-offline

# Copiamos el código fuente y compilamos el .jar saltando los tests para ir más rápido
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Etapa 2: Ejecución (Runtime)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]