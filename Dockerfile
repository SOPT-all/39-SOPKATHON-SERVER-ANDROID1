FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

COPY . .
RUN sh ./gradlew --no-daemon bootJar \
    && cp "$(find build/libs -maxdepth 1 -name '*.jar' ! -name '*-plain.jar' -print -quit)" /app.jar

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app.jar /app/app.jar
USER 10001:10001

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]