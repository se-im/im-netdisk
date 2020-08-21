FROM openjdk:8-jdk-alpine

VOLUME /tmp

COPY  target/*.jar app.jar

#ENV spring.profile.active=docker

EXPOSE 8010
ENTRYPOINT ["java","-jar","/app.jar"]