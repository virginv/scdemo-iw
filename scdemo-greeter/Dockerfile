FROM openjdk:8-jre-alpine
VOLUME /tmp
MAINTAINER virginv
ENV APP_FILE scdemo-greeter-0.1.0.jar
ENV APP_HOME /usr/app
COPY target/$APP_FILE $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]
