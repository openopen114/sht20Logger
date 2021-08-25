FROM dockerfile/java:oracle-java8

VOLUME /tmp
ADD sht20Logger.jar /opt/sht20Logger/
EXPOSE 8080
WORKDIR /opt/sht20Logger/
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Xms512m", "-Xmx1g", "-jar", "sht20Logger.jar"]
