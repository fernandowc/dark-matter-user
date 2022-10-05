FROM openjdk:15.0.1

WORKDIR /app

COPY ./target/dark-matter-user-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "dark-matter-user-0.0.1-SNAPSHOT.jar"]

