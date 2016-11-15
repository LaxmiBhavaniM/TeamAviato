echo 'starting installation process' >> /var/log/sga-teamaviato-StormDetector-install.log
#cd '/home/ec2-user/stormdetection'

rm -r /home/ec2-user/stormDetector
mv /home/ec2-user/StormDetector  /home/ec2-user/stormDetector
cd /home/ec2-user/stormDetector/
chmod 777 stormdetection
cd stormdetection

setup bridge network
docker network ls | grep 'mynet123'
if [ $? ne 0 ]; then
  docker network create --subnet=172.18.0.0/16 mynet123
fi

docker build -t sdetect_img .
docker run -d --net mynet123 --ip 172.18.0.31 -p 8000:8000 --name api-sdetect sdetect_img >> sga-teamaviato-StormDetector-docker-server.log 2>&1 &


