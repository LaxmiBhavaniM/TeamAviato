# copy files from Gateway to a new folder
mkdir '/home/ec2-user/gateway-microservice'
cp -r /home/ec2-user/Gateway/* /home/ec2-user/gateway-microservice

# delete the revision directory
rm -rf '/home/ec2-user/Gateway'

#cd '/home/ec2-user/Gateway'

#echo "Installing the dependencies from package.json"


#echo "starting the application run weather forecast"


cd '/home/ec2-user/gateway-microservice/Gateway_Aviato'

docker build -t gateway . > /var/log/gatewayimagebuild.log 2>&1 &
docker images | grep '<none>' | awk '{print $3}' | xargs --no-run-if-empty docker rmi -f 2>&1 > /var/log/gatewayrmi.log 2>&1
#docker rmi -f $(docker images | grep '^<none>' | awk '{print $3}') >> /var/log/dockergatewayimage.log 2>&1 &
docker ps -a | grep 'api-g' | awk '{print $1}' | xargs --no-run-if-empty docker rm -f 2>&1 > /var/log/gatewayrm.log 
docker run -d -p 4000:3000 --name api-g gateway 2>&1 > /var/log/gateway.log

#echo "Fixing node-sass"

#npm uninstall --save node-sass
#npm install --save node-sass

#chmod -R 777 public

#node app.js >> debug.log 2>&1 &
