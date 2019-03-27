# CloudZ Database Portal UI
CloudZ Database Portal UI

## Build & Dockerize
```
$ mvn clean install -Dmaven.test.skip=true  docker:build

$ bx cr login
$ docker image tag zdb-portal-ui:latest registry.au-syd.bluemix.net/zdb-dev/zdb-portal-ui:latest
$ docker image push registry.au-syd.bluemix.net/zdb-dev/zdb-portal-ui:latest
```

docker image tag zdb-portal-ui:<VERSION> registry.au-syd.bluemix.net/cloudzdb/zdb-portal-ui:<VERSION>
docker image push registry.au-syd.bluemix.net/cloudzdb/zdb-portal-ui:<VERSION>


docker image tag zdb-portal-ui registry.au-syd.bluemix.net/cloudzdb/zdb-portal-ui:1.1.0
docker image push registry.au-syd.bluemix.net/cloudzdb/zdb-portal-ui:1.1.0


## Deployment
```
$ kubectl delete deployment zdb-portal-ui-deployment -n zdb-system
$ kubectl apply -f ./deploy/zdb-system-deployment.yml
```