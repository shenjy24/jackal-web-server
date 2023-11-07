FROM openjdk:17-jdk-alpine
VOLUME /tmp
ADD web.jar /app.jar
ADD conf /conf
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.config.location=/conf/application.yml"]