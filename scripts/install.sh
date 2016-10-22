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
sudo docker login -e="sneha.tilak26@gmail.com" -u="tilaks" -p="teamAviato"
sudo docker pull tilaks/dataingestor
sudo docker run -d -p 8009:8080 --name api-di $(sudo docker images | grep tilaks/dataingestor | awk '{print $3}') >> /var/log/dataingestor.log 2>&1 &
