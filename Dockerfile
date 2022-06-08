FROM openjdk:8
EXPOSE 8081
ADD target/project2app.jar p2app.jar
ENTRYPOINT ["java", "-jar", "/p2app.jar"]