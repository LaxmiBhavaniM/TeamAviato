echo 'Kill All the Process' >> /var/log/flask-before.log
ps -ef | grep python | grep -v grep | awk '{print $2}' | xargs kill >> /var/log/flask-before.log
sleep 5