FROM gradle:7-jdk-alpine as BUILDER
ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /build/
COPY build.gradle /build/
COPY src /build/src/

RUN gradle build
COPY build/libs/CarApp-${VERSION}.jar target/application.jar

FROM openjdk:17-jdk-slim
WORKDIR /app/

COPY --from=BUILDER /build/target/application.jar /app/
CMD java -jar /app/application.jar