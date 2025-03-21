FROM node:23 AS buildang

WORKDIR /src

COPY client/*.json .
COPY client/public public
COPY client/src src

RUN npm ci 
RUN npm i -g @angular/cli
RUN ng build

FROM eclipse-temurin:23-jdk-noble AS buildjava

WORKDIR /src

COPY server/mvnw .
COPY server/pom.xml .
COPY server/src src
COPY server/.mvn .mvn

COPY --from=buildang /src/dist/client/browser/* src/main/resources/static

RUN chmod a+x mvnw && /src/mvnw package -Dmaven.test.skip=true

FROM eclipse-temurin:23-jre-noble

WORKDIR /app

COPY --from=buildjava /src/target/server-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=3000
ENV MYSQL_URL=
ENV MYSQL_USERNAME=
ENV MYSQL_PASSWORD=
ENV MONGODB_URI=
ENV MONGODB_DB=

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar