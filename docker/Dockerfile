FROM java:8
ENV SPRING_ACTIVE_PROFILE prod
ADD dist/zdb-portal-ui-1.0.0-SNAPSHOT.war zdb-portal-ui.war 
VOLUME /tmp
VOLUME /target
RUN bash -c 'touch /zdb-portal-ui.war'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=${SPRING_ACTIVE_PROFILE}","-jar","/zdb-portal-ui.war"]