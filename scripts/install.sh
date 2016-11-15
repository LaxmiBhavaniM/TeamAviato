echo 'starting installation process' >> /var/log/sga-teamaviato-RunForecast-install.log
#cd '/home/ec2-user/runforecast'

rm -r /home/ec2-user/runForecast
mv /home/ec2-user/RunForecast  /home/ec2-user/runForecast
cd /home/ec2-user/runForecast/
chmod 777 runforecast
cd runforecast

setup bridge network
docker network ls | grep 'mynet123'
if [ $? ne 0 ]; then
  docker network create --subnet=172.18.0.0/16 mynet123
fi

docker build -t runforecast_img .
docker run -d --net mynet123 --ip 172.18.0.34 -p 8050:8050 --name api-runforecast runforecast_img >> sga-teamaviato-RUnForecast-docker-server.log 2>&1 &
