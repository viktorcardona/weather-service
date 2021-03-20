FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE
RUN mkdir /opt/app
COPY target/${JAR_FILE} /opt/app/service.jar
EXPOSE 8082
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/opt/app/service.jar"]