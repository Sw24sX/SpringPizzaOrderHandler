FROM eclipse-temurin:21-jre-alpine
COPY /build/libs/SpringPizzaOrderHandler-*-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]