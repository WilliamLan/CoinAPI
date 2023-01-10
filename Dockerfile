FROM openjdk:8-jdk-alpine
WORKDIR /docker
COPY ./target/CoinDeskAPI.jar /docker/CoinDeskAPI.jar
ENTRYPOINT ["java","-jar","CoinDeskAPI.jar"]