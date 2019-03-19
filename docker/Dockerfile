FROM java:8
ENV SPRING_ACTIVE_PROFILE prod
ENV APP_VERSION 1.0.7
ENV JAVA_OPTS "-Xms1536m -Xmx1536m"
ADD dist/zdb-portal-ui-1.0.7.war zdb-portal-ui-1.0.7.war 
VOLUME /tmp
VOLUME /target
RUN bash -c 'touch /zdb-portal-ui-1.0.7.war'
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=${SPRING_ACTIVE_PROFILE}","-jar","/zdb-portal-ui-1.0.7.war"]
ENTRYPOINT exec java $JAVA_OPTS -Dspring.profiles.active=${SPRING_ACTIVE_PROFILE} -jar ./zdb-portal-ui-1.0.7.war
