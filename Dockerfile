FROM amazoncorretto:17-alpine-jdk

WORKDIR /app
COPY ./pom.xml /app

ENV MVNW=/app/mvnw
COPY ./.mvn /app/.mvn
COPY ./mvnw /app/mvnw
COPY ./src /app/src

RUN $MVNW clean package -DskipTests

COPY /*/target/backend-template-api-*.jar ./app.jar
EXPOSE 8001

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/target/*.jar"]
