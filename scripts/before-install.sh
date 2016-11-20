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

install_dir="/usr/local"
dir="/usr/local/zookeeper-3.4.8"
if [ ! -d "$dir" ] ; then
	cd "$install_dir"
	wget http://www-us.apache.org/dist/zookeeper/stable/zookeeper-3.4.8.tar.gz
	tar xzf zookeeper-3.4.8.tar.gz 
	rm zookeeper-3.4.8.tar.gz 
	cd "$dir/conf"
	sudo touch zoo.cfg
	echo "tickTime=2000" > zoo.cfg
	echo "initLimit=10" >> zoo.cfg
	echo "syncLimit=5" >> zoo.cfg
	echo "dataDir=/var/lib/zookeeper" >> zoo.cfg
	echo "clientPort=2181" >> zoo.cfg
	echo "server.1=52.15.57.97:2888:3888" >> zoo.cfg
	echo "server.2=35.164.24.104:2888:3888" >> zoo.cfg
	echo "server.3=52.15.40.136:2888:3888" >> zoo.cfg
fi

cd "$dir/bin"
./zkServer.sh stop
./zkServer.sh start

sudo yum install -y docker-io
sudo service docker start

#Remove existing containers if any
docker ps -a | grep -w "api-di" | awk '{print $1}' | xargs --no-run-if-empty docker stop
docker ps -a | grep -w "api-di" | awk '{print $1}' | xargs --no-run-if-empty docker rm

#Remove existing images if any
docker images | grep -w "tilaks/dataingestor" | awk '{print $3}' | xargs --no-run-if-empty docker rmi -f
