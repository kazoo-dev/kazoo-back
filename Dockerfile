FROM maven:3-ibmjava-8-alpine as build
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:8-jre-alpine
COPY --from=build target/kazoo-*.jar ./
CMD ["java", "-jar", "./kazoo-0.0.1-SNAPSHOT.jar"]
