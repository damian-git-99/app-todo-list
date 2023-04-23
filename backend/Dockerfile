FROM maven:3.6.3 as builder
WORKDIR /app
COPY ./backend/pom.xml .
RUN mvn clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
COPY ./backend/src ./src
RUN mvn clean package -Dmaven.test.skip


FROM openjdk:11-slim-buster
WORKDIR /app
COPY --from=builder /app/target/app-todo-backend-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar","app-todo-backend-0.0.1-SNAPSHOT.jar"]