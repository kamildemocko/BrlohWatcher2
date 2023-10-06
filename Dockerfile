FROM openjdk
WORKDIR /app
COPY ./out/artifacts/brloh_watcher2_jar/brloh_watcher2.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]