echo 'killing existing process on port 31000 if any'
sudo kill -9 $(sudo lsof -i :31000 | grep LISTEN) >> /var/log/processkill.log 2>&1 &

echo 'Removing existing docker instances' >> /var/log/scluster-docker.log 2>&1
docker ps -a | grep 'scluster_img' | awk '{print $1}' | xargs --no-run-if-empty docker stop
docker ps -a | grep 'scluster_img' | awk '{print $1}' | xargs --no-run-if-empty docker rm