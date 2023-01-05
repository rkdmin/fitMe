FROM openjdk:11-jre-slim
COPY build/libs/fitMe-0.0.1-SNAPSHOT.jar fitme.jar
ENTRYPOINT ["java", "-jar", "fitme.jar"]