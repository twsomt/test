FROM openjdk:latest

WORKDIR /app

COPY Main.java /app/

RUN javac Main.java

CMD ["java", "Main"]
