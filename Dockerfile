FROM gradle:8.4.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:17
EXPOSE 10002:10002
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/application.jar
ENTRYPOINT ["java","-jar","/app/application.jar"]