FROM openjdk:21-jdk-oracle
VOLUME /web
COPY target/web.jar /web/jar/web.jar
COPY target/classes/application-product.yml /web/conf/application.yml
ENTRYPOINT ["java","-jar","/web/jar/web.jar","--spring.config.location=/web/conf/application.yml"]