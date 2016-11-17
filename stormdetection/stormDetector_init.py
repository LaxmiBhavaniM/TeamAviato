import json
import calendar, datetime, time
from datetime import datetime
from kazoo.client import KazooClient
import logging
import uuid

from kazoo.exceptions import (
    AuthFailedError,
    ConfigurationError,
    ConnectionClosedError,
    ConnectionLoss,
    NoNodeError,
    NodeExistsError,
    SessionExpiredError,
    WriterNotClosedException,
)

logging.basicConfig()
zk = KazooClient(hosts='ec2-35-160-243-251.us-west-2.compute.amazonaws.com:2181')
#zk = KazooClient(hosts='localhost:2181')

zk.start()
#********** register service with zookeeper *********
serviceName = "stormDetector"
ipaddress = "localhost"
serviceURI = "/stormdetector/v1/service"
port = 8000
path = "http://"+ipaddress+":"+str(port)+":"+serviceName
try:    #create base
    zk.create('/weather-predictor')
except Exception as e1:
    print("Error while creating Weather-predictor znode",e1)
    pass
try:    #create base
    zk.create('/weather-predictor/stormDetector')
except Exception as e1:
    print("Error while creating /weather-predictor/stormDetector znode",e1)
    pass
zk.ensure_path("/weather-predictor/stormDetector")
print(zk.client_id)

try:
    uniqueid=str(uuid.uuid4())
    zk.create('/weather-predictor/stormDetector/'+uniqueid,
              json.dumps({'name': serviceName, 'id': uniqueid, 'address': ipaddress, 'port': port,
                          'sslPort': None, 'payload': None,
                          'registrationTimeUTC': (datetime.utcnow() - datetime.utcfromtimestamp(0)).total_seconds(),
                          'path': path, 'serviceType': 'DYNAMIC',
                          "uriSpec": {
                              "parts": [{"value": path, "variable=": False}]
                          }}, ensure_ascii=True).encode(),
              ephemeral=True)

except Exception as e2:
    print("Error while creating weather-predictor/stormDetector znode",e2)
#******************REGISTERED************

while True:
    time.sleep(5)
#input("enter to exit")