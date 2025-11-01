# Use official Tomcat image with JDK 17
FROM tomcat:10.1-jdk17-corretto

LABEL maintainer="Gandham Adikesava Naidu <adikesavanaidug2404@gmail.com>"

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file
COPY target/colorful-webapp.war /usr/local/tomcat/webapps/ROOT.war

# Change Tomcat port from 8080 to 8083
RUN sed -i 's/8080/8083/g' /usr/local/tomcat/conf/server.xml

# Expose new port
EXPOSE 8083

CMD ["catalina.sh", "run"]
