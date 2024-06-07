FROM openjdk:17

WORKDIR /app

COPY target/movie-studio.jar /app/movie-studio.jar

EXPOSE 9999

ENTRYPOINT ["java", "-jar", "/app/movie-studio.jar"]