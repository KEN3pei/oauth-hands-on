# # 参照(https://www.baeldung.com/spring-docker-liberica)
# FROM bellsoft/liberica-runtime-container:jdk-17-glibc AS build
# # openjdk:17だとxargs is not availableとかいうエラーが出る
# COPY ./ /home/gradle/src/
# WORKDIR /home/gradle/src
# RUN ./gradlew build

# FROM openjdk:17
FROM --platform=linux/x86_64 bellsoft/liberica-runtime-container:jdk-17-glibc
WORKDIR /app
# COPY --from=build /home/gradle/src/build/libs/*.jar ./spring-boot-application.jar
COPY build/libs/*.jar ./spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "spring-boot-application.jar"]