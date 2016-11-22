export JAVA_HOME=/usr/lib/jvm/jre-1.7.0-openjdk.x86_64

echo 'check if tomcat is installed'
cd /usr/local/tomcat7/apache-tomcat-7.0.73 
sudo ./bin/version.sh
if [ "$?" -ne 0 ]; then
   echo 'Installing Tomcat...'
   	sudo wget http://www.us.apache.org/dist/tomcat/tomcat-7/v7.0.73/bin/apache-tomcat-7.0.73.tar.gz
	tar xzf apache-tomcat-7.0.73.tar.gz
	sudo mkdir /usr/local/tomcat7
	sudo mv apache-tomcat-7.0.73 /usr/local/tomcat7
	cd /usr/local/tomcat7/apache-tomcat-7.0.73
	sudo ./bin/startup.sh
	sudo ./bin/version.sh
else
   echo 'Killing existing tomcat process if any'
	cd /usr/local/tomcat7/apache-tomcat-7.0.73
	sudo sh ./bin/shutdown.sh
	sleep 20
fi

echo 'check if maven is installed'
mvn --version
if [ "$?" -ne 0 ]; then
    echo 'Installing Maven...'
	sudo wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
	sudo sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
	sudo yum install -y apache-maven
	mvn --version
fi

#Check if MySQL is already installed
rpm -qa | grep mysql

if [ "$?" -ne 0 ]; then
     echo 'Installing MySQL...'
     sudo yum install -y mysql mysql-server

     echo 'Change directory to that of MySQL'
     cd /etc/rc.d/init.d/
     sudo service mysqld status
     sudo service mysqld start
	
     #Check if MySQL is already installed
     rpm -qa | grep mysql
fi

mysql -u root -p
echo 'root'

#Drop existing database
echo 'DROP DATABASE IF EXISTS registry;'

#Create database
echo 'CREATE DATABASE IF NOT EXISTS registry;'

#Use database
echo 'use registry;'

#Create table
echo 'CREATE TABLE logservice
(
timestamp VARCHAR(45) NOT NULL,
requestid TINYTEXT NOT NULL,
username VARCHAR(45) NOT NULL,
servicename VARCHAR(45) NOT NULL,
description MEDIUMTEXT NOT NULL,
PRIMARY KEY (timestamp)
);'

#Exit from database
echo 'exit;'

