FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.war app.war
ENTRYPOINT ["java","-war","/app.war"]
EXPOSE 8080