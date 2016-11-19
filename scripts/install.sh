echo 'starting installation process' >> /var/log/sga-teamaviato-StormDetector-install.log
#cd '/home/ec2-user/stormdetection'

# copy artifacts to new dir
mkdir '/home/ec2-user/stormdetector-microservice'
cp -r /home/ec2-user/stormdetection/* /home/ec2-user/stormdetector-microservice

# delete revision
rm -rf '/home/ec2-user/stormdetection'

echo 'Installing the Storm Detector API...'
cd '/home/ec2-user/stormdetector-microservice/stormdetection'


cd '/home/ec2-user/docker'
docker login -e="laxmibh.malkareddy@gmail.com" -u="laxmibhavanim" -p="laxmalka"
docker pull laxmibhavanim/stormdetector
docker images | grep '<none>' | awk '{print $3}' | xargs --no-run-if-empty docker rmi -f
docker run -d -p 8000:8000 --name api-sdetect $(docker images | grep -w "laxmibhavanim/stormdetector" | awk '{print $3}') >> /var/log/stormdetector.log 2>&1 &



