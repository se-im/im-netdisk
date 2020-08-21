FROM openjdk:8-jdk-alpine

VOLUME /home/zmz/netdisk/

COPY  target/*.jar app.jar

#ENV spring.profile.active=docker

EXPOSE 8010
ENTRYPOINT ["java","-jar","/app.jar"]