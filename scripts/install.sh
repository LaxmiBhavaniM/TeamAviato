echo 'starting installation process' >> /var/log/sga-teamaviato-StormDetection-install.log
#cd '/home/ec2-user/stormclustering'
sudo su

rm -r /home/ec2-user/stormDetector
mv /home/ec2-user/StormDetector  /home/ec2-user/stormDetector
cd /home/ec2-user/stormDetector/
chmod 777 stormdetection
cd stormdetection

docker build -t sdetect_img .
docker run -d -p 8000:8000 --name api-sdetect sdetect_img >> sga-teamaviato-StormDetection-docker-server.log 2>&1 &
