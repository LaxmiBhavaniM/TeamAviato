
echo 'killing existing process on port 8000 if any'
sudo kill -9 $(sudo lsof -i :8000 | grep LISTEN) >> /var/log/processkill.log 2>&1 &

install_dir="/usr/local"
dir="/usr/local/zookeeper-3.4.9"
if [ ! -d "$dir" ] ; then
	cd "$install_dir"
	wget http://www-us.apache.org/dist/zookeeper/stable/zookeeper-3.4.9.tar.gz
	tar xzf zookeeper-3.4.9.tar.gz 
	rm zookeeper-3.4.9.tar.gz 
	cd zookeeper-3.4.9/conf
	sudo mv zoo_sample.cfg zoo.cfg
	oldString="dataDir=/temp/zookeeper"
	newString="dataDir=/var/lib/zookeeper"
	sed -i "s/$oldString/$newString/g" zoo.cfg
	cd ..
fi

cd "$dir/bin"
./zkServer.sh stop
./zkServer.sh start

sudo yum install -y docker-io
sudo service docker start

echo 'Removing existing docker instances' >> /var/log/sdetector-docker.log 2>&1
docker ps -a | grep 'sdetect_img' | awk '{print $1}' | xargs --no-run-if-empty docker stop
docker ps -a | grep 'sdetect_img' | awk '{print $1}' | xargs --no-run-if-empty docker rm

#Remove existing images if any
docker images | grep -w "laxmibhavanim/stormdetector" | awk '{print $3}' | xargs --no-run-if-empty docker rmi -f