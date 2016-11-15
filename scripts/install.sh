# copy artifacts to new dir
mkdir '/home/ec2-user/registry-microservice'
cp -r /home/ec2-user/registry/* /home/ec2-user/registry-microservice

# delete revision
rm -rf '/home/ec2-user/registry'

echo 'Installing the Registry API...' 
cd '/home/ec2-user/registry-microservice/registry/'

sudo mvn clean install >> /var/log/tomcat.log
sudo cp target/*.war /usr/local/tomcat7/apache-tomcat-7.0.72/webapps/ >> /var/log/tomcat.log
cd  /usr/local/tomcat7/apache-tomcat-7.0.72

sudo sh ./bin/startup.sh >> /var/log/tomcat.log 2>&1 &
