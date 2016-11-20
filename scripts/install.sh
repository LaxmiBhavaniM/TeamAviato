echo 'Installing the Data Ingestor API...'
cd '/home/ec2-user/router-microservice/delegate'

mvn clean install >> /var/log/tomcat.log
mvn compile war:war

cd '/home/ec2-user/docker'
sudo docker login -e="sneha.tilak26@gmail.com" -u="tilaks" -p="teamAviato"
sudo docker pull tilaks/router
sudo docker images | grep '<none>' | awk '{print $3}' | xargs --no-run-if-empty docker rmi -f
sudo docker run -d -p 7000:8080 --name route $(docker images | grep -w "tilaks/router" | awk '{print $3}') >> /var/log/router.log 2>&1 &
