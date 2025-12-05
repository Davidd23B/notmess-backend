FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY *.jar app.jar

RUN mkdir -p /app/imagenes

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

