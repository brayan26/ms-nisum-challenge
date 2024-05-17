FROM amazoncorretto:17-alpine-jdk as builder

WORKDIR /app
COPY ./pom.xml /app

ENV MVNW=/app/mvnw
COPY ./.mvn /app/.mvn
COPY ./mvnw /app/mvnw
COPY ./src /app/src

RUN $MVNW clean package -DskipTests

FROM amazoncorretto:17-alpine-jdk

COPY --from=builder /app/target/backend-nisum-api-*.jar ./app/app.jar
EXPOSE 8001

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]