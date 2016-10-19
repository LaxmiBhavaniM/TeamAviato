echo 'starting installation process' >> /var/log/sga-teamaviato-RunForecast-install.log
#cd '/home/ec2-user/runforecast'

rm -r /home/ec2-user/runForecast
mv /home/ec2-user/RunForecast  /home/ec2-user/runForecast
cd /home/ec2-user/runForecast/
chmod 777 runforecast
cd runforecast

echo 'Activating virtualenv for RunForecast Microservice' >> /var/log/sga-teamaviato-RunForecast-install.log
pip install virtualenv >> /var/log/sga-teamaviato-RunForecast-install.log
virtualenv env >> /var/log/sga-teamaviato-RunForecast-install.log
source env/bin/activate >> /var/log/sga-teamaviato-RunForecast-install.log
pip install Flask >> /var/log/sga-teamaviato-RunForecast-install.log
pip install nose >> /var/log/sga-teamaviato-RunForecast-install.log
pip install Flask-SQLAlchemy >> /var/log/sga-teamaviato-RunForecast-install.log
echo 'Running Flask Server' >> /var/log/sga-teamaviato-RunForecast-install.log
pip install ConfigParser
pip install requests

nohup python runforecast.py > /dev/null 2>&1 &