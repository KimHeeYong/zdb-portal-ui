FROM java:8
VOLUME /tmp
ADD zdb-portal-ui-1.0.0-SNAPSHOT.jar zdb-portal-ui.jar
RUN bash -c 'touch /zdb-portal-ui.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/zdb-portal-ui.jar"]