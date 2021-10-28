FROM openjdk:11
ENV MYSQL_URL
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT java -jar app.jar