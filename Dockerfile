FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

# testando para corrigir esse erro
FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080


COPY --from=build /target/revenda_motos-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java","-jar", "app.jar" ]
