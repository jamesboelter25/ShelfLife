# I need this file to put my app on Render (it needs Docker to run Java/Tomcat)

# step 1 - build my .war file with maven
FROM maven:3.9-eclipse-temurin-17 AS build
COPY . /app
WORKDIR /app
RUN mvn -q package

# step 2 - put my .war in tomcat 10.1
# I named it ROOT.war so my app is the main page of the site
FROM tomcat:10.1-jdk17
COPY --from=build /app/target/ShelfLife.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
