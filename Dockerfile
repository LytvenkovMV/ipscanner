# Fetching version of Java
FROM openjdk:14

# Setting up work directory
WORKDIR /java

# Copy the jar file into our app
COPY ./target/ipscanner-1.0-SNAPSHOT.jar /java

# Exposing port 7070
EXPOSE 7070

# Starting the application
CMD ["java", "-jar", "target/ipscanner-1.0-SNAPSHOT.jar"]