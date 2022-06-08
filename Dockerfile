FROM openjdk:8
EXPOSE 8081
ADD target/revature-project1-0.0.1-SNAPSHOT.jar p2app.jar
ENTRYPOINT ["java", "-jar", "/p2app.jar"]