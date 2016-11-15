export JAVA_HOME=/usr/lib/jvm/jre-1.7.0-openjdk.x86_64

#echo 'check if tomcat is installed'
#cd /usr/local/tomcat7/apache-tomcat-7.0.72 
#sudo ./bin/version.sh
#if [ "$?" -ne 0 ]; then
#   echo 'Installing Tomcat...'
#   	sudo wget http://www.us.apache.org/dist/tomcat/tomcat-7/v7.0.72/bin/apache-tomcat-7.0.72.tar.gz
#	tar xzf apache-tomcat-7.0.72.tar.gz
#	sudo mkdir /usr/local/tomcat7
#	sudo mv apache-tomcat-7.0.72 /usr/local/tomcat7
#	cd /usr/local/tomcat7/apache-tomcat-7.0.72
#	sudo ./bin/startup.sh
#	sudo ./bin/version.sh
#else
#   echo 'Killing existing tomcat process if any'
#	cd /usr/local/tomcat7/apache-tomcat-7.0.72
#	sudo sh ./bin/shutdown.sh
#	sleep 20
#fi

echo 'check if maven is installed'
mvn --version
if [ "$?" -ne 0 ]; then
    echo 'Installing Maven...'
	sudo wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
	sudo sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
	sudo yum install -y apache-maven
	mvn --version
fi

sudo yum install -y docker-io
sudo service docker start

#Remove existing containers if any
docker ps -a | grep -w "api-di" | awk '{print $1}' | xargs --no-run-if-empty docker stop
docker ps -a | grep -w "api-di" | awk '{print $1}' | xargs --no-run-if-empty docker rm

#Remove existing images if any
docker images | grep -w "tilaks/dataingestor" | awk '{print $3}' | xargs --no-run-if-empty docker rmi -f
