# copy artifacts to new dir
mkdir '/home/ec2-user/ingestor-microservice'
cp -r /home/ec2-user/api-ingestor/* /home/ec2-user/ingestor-microservice

# delete revision
rm -rf '/home/ec2-user/api-ingestor'

echo 'Installing the Data Ingestor API...'
cd '/home/ec2-user/ingestor-microservice/api-ingestor'

mvn clean install >> /var/log/tomcat.log
mvn compile war:war
#cp target/*.war /usr/local/tomcat7/apache-tomcat-7.0.72/webapps/ >> /var/log/tomcat.log
#cd  /usr/local/tomcat7/apache-tomcat-7.0.72

#sudo sh ./bin/startup.sh >> /var/log/tomcat.log 2>&1 &

#cd '/home/ec2-user/ingestor-microservice/api-ingestor'

cd '/home/ec2-user/docker'
docker login -e="sneha.tilak26@gmail.com" -u="tilaks" -p="teamAviato"
docker pull tilaks/dataingestor
docker images | grep '<none>' | awk '{print $3}' | xargs --no-run-if-empty docker rmi -f
docker run -d -p 9000:8080 --name api-di $(docker images | grep -w "tilaks/dataingestor" | awk '{print $3}') >> /var/log/dataingestor.log 2>&1 &
