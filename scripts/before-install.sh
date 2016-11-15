echo 'killing existing process on port 8050 if any'
sudo kill -9 $(sudo lsof -i :8050 | grep LISTEN) >> /var/log/processkill.log 2>&1 &

echo 'Removing existing docker instances' >> /var/log/runforecast-docker.log 2>&1
docker ps -a | grep 'runforecast_img' | awk '{print $1}' | xargs --no-run-if-empty docker stop
docker ps -a | grep 'runforecast_img' | awk '{print $1}' | xargs --no-run-if-empty docker rm