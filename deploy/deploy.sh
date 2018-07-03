cd $HOME/git/zdb.ui.server
echo ""
echo "=============================="
echo "docker:build"
echo "=============================="
mvn clean install -P zcp-demo -Dmaven.test.skip=true docker:build

#bx cr login
echo ""
echo "=============================="
echo "        tagging, push"
echo "=============================="
docker image tag zdb-portal-ui:latest registry.au-syd.bluemix.net/zdb-dev/zdb-portal-ui:latest
docker image push registry.au-syd.bluemix.net/zdb-dev/zdb-portal-ui:latest

bx cr image-list
echo ""
echo "=============================="
echo "del old version service [zdb-portal-deployment]"
echo "=============================="
kubectl delete deployment zdb-portal-deployment -n zdb-system
echo ""
echo "=============================="
echo "sleep 5s"
echo "=============================="
sleep 5
echo ""
echo "=============================="
echo "create new version service [zdb-portal-deployment]"
echo "=============================="
kubectl apply -f zdb-system-deployment.yml
