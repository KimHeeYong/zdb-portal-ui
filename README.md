# zdb-portal-ui
zdb-portal-ui

## Build & Dockerize
mvn clean install -Dmaven.test.skip=true  docker:build

#bx cr login
docker image tag zdb-portal-ui:latest registry.au-syd.bluemix.net/zdb-dev/zdb-portal-ui:latest
docker image push registry.au-syd.bluemix.net/zdb-dev/zdb-portal-ui:latest

## Deployment
kubectl delete deployment zdb-portal-ui-deployment -n zdb-system
kubectl apply -f ./deploy/zdb-system-deployment.yml