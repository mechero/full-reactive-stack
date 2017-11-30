FROM openjdk:8-jre-alpine
COPY ./target/reactive-web-1.0.0-SNAPSHOT.jar /usr/src/spring-boot-reactive-web/
WORKDIR /usr/src/spring-boot-reactive-web
CMD ["java", "-jar", "reactive-web-1.0.0-SNAPSHOT.jar"]