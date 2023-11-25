FROM openjdk:17-jdk-alpine
VOLUME /tmp
ADD web.jar /web.jar
# conf下放置了application.yml和logback-spring.xml
ADD conf /conf
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/web.jar","--spring.config.location=/conf/application.yml"]