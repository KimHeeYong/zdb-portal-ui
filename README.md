# CloudZ DB Portal UI
CloudZ DB Portal UI

## Build & Dockerize
```
mvn clean install -Dmaven.test.skip=true  docker:build

```



## Deployment
```
$ kubectl apply -f ./deploy/zdb-system-deployment.yml
```