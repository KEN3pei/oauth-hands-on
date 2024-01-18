# # 参照(https://www.baeldung.com/spring-docker-liberica)
FROM --platform=linux/x86_64 bellsoft/liberica-runtime-container:jdk-17-glibc
WORKDIR /app
EXPOSE 8080
COPY build/libs/*.jar ./spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "spring-boot-application.jar"]