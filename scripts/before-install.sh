echo 'killing existing process on port 8000 if any'
sudo kill -9 $(sudo lsof -i :8000 | grep LISTEN) >> /var/log/processkill.log 2>&1 &

sudo su

echo 'check if Zookeeper is installed'
install_dir="/usr/local"
dir="/usr/local/zookeeper-3.4.8"
if [ ! -d "$dir" ] ; then
        cd "$install_dir"
        sudo wget http://www-us.apache.org/dist/zookeeper/zookeeper-3.4.8/zookeeper-3.4.8.tar.gz
        sudo tar xzf zookeeper-3.4.8.tar.gz
        sudo rm zookeeper-3.4.8.tar.gz
        cd "$dir/conf"
        sudo touch zoo.cfg
        sudo chmod 777 zoo.cfg
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

echo 'Removing existing docker instances' >> /var/log/scluster-docker.log 2>&1
docker ps -a | grep 'sdetect_img' | awk '{print $1}' | xargs --no-run-if-empty docker stop
docker ps -a | grep 'sdetect_img' | awk '{print $1}' | xargs --no-run-if-empty docker rm
