FROM java:8
ENV SPRING_ACTIVE_PROFILE prod
ENV APP_VERSION 1.0.4
ADD dist/zdb-portal-ui-1.0.4.war zdb-portal-ui-1.0.4.war 
VOLUME /tmp
VOLUME /target
RUN bash -c 'touch /zdb-portal-ui-1.0.4.war'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=${SPRING_ACTIVE_PROFILE}","-jar","/zdb-portal-ui-1.0.4.war"]