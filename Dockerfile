FROM tomcat:9.0-jre8-temurin-focal
EXPOSE 8080
RUN rm -rf /usr/local/tomcat/webapps/*
ARG WAR_FILE=build/libs/*.war
COPY ${WAR_FILE} /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh","run"]