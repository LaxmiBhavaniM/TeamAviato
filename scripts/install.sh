echo 'starting installation process' >> /var/log/sga-teamaviato-ForecastTrigger-install.log
#cd '/home/ec2-user/forecasttrigger'

rm -r /home/ec2-user/forecastTrigger
mv /home/ec2-user/ForecastTrigger  /home/ec2-user/forecastTrigger
cd /home/ec2-user/forecastTrigger/
chmod 777 forecasttrigger
cd forecasttrigger

setup bridge network
docker network ls | grep 'mynet123'
if [ $? ne 0 ]; then
  docker network create --subnet=172.18.0.0/16 mynet123
fi

docker build -t ftrigger_img .
#docker run -d -p 32000:32000 --name api-ftrigger ftrigger_img >> sga-teamaviato-ForecastTrigger-docker-server.log 2>&1 &
docker run -d --net mynet123 --ip 172.18.0.33 -p 32000:32000 --name api-ftrigger ftrigger_img >> sga-teamaviato-ForecastTrigger-docker-server.log 2>&1 &
