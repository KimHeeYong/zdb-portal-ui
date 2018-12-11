FROM java:8
ENV SPRING_ACTIVE_PROFILE prod
ENV APP_VERSION=1.0.4
ADD dist/zdb-portal-ui-${APP_VERSION}-SNAPSHOT.war zdb-portal-ui-${APP_VERSION}.war 
VOLUME /tmp
VOLUME /target
RUN bash -c 'touch /zdb-portal-ui-${APP_VERSION}.war'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=${SPRING_ACTIVE_PROFILE}","-jar","/zdb-portal-ui-${APP_VERSION}.war"]