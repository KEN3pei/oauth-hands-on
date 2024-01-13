# 参照(https://www.baeldung.com/spring-docker-liberica)
FROM bellsoft/liberica-runtime-container:jdk-17-glibc AS build
# openjdk:17だとxargs is not availableとかいうエラーが出る
COPY ./ /home/gradle/src/
WORKDIR /home/gradle/src
RUN ./gradlew build --stacktrace

FROM openjdk:17
WORKDIR /app
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*.jar ./spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "spring-boot-application.jar"]