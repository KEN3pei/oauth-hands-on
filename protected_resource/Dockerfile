# 参照(https://www.baeldung.com/spring-docker-liberica)
# FROM bellsoft/liberica-runtime-container:jdk-17-glibc AS build
# COPY --chown=gradle:gradle ./ /home/gradle/src/
# WORKDIR /home/gradle/src
# RUN apk add gradle
# RUN gradle wrapper --gradle-version 8.4
# RUN ./gradlew build --stacktrace --scan

# FROM openjdk:17
FROM --platform=linux/x86_64 bellsoft/liberica-runtime-container:jdk-17-glibc
WORKDIR /app
EXPOSE 8080
# COPY --from=build /home/gradle/src/build/libs/*.jar ./spring-boot-application.jar
COPY build/libs/*.jar ./spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "spring-boot-application.jar"]