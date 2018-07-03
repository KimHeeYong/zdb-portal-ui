FROM java:8
ENV SPRING_ACTIVE_PROFILE zcp-demo
ADD dist/zdb-portal-ui-1.0.0-SNAPSHOT.jar zdb-portal-ui.jar
VOLUME /tmp
VOLUME /target
RUN bash -c 'touch /zdb-portal-ui.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=${SPRING_ACTIVE_PROFILE}","-jar","/zdb-portal-ui.jar"]