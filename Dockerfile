FROM openjdk:17-alpine3.12
MAINTAINER agustinpriano
ARG WAR_FILE=target/*.war
COPY ${WAR_FILE} challenge-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java","-jar","/challenge-0.0.1-SNAPSHOT.war"]
