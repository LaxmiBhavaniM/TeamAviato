from configparser import ConfigParser

import requests
from flask import Flask
from flask import Response
from flask import json
from flask import request

import json
import calendar, datetime, time
from datetime import datetime
from kazoo.client import KazooClient
import logging
import uuid
import urllib.request
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

def register_to_zookeeper():
    logging.basicConfig(filename='zkregistry.log', level=logging.DEBUG, format="%(asctime)s - %(name)s - %(message)s",
                        datefmt="%H:%M:%S", filemode='w')

    host = urllib.request.urlopen("http://169.254.169.254/latest/meta-data/public-ipv4").read().decode('utf-8')
    zport=2181
    zurl=host+":"+str(zport)
    zk = KazooClient(hosts=zurl)
    # zk = KazooClient(hosts='localhost:2181')
    zk.start()

    # ********** register service with zookeeper *********
    serviceName = "stormDetector"
    ipaddress = host
    serviceURI = "/stormdetector/v1/service"
    port = 8000
    path = "http://" + ipaddress + ":" + str(port) + ":" + serviceName

    try:  # create base
        zk.create('/weather-predictor')
    except Exception as e1:
        print("Error while creating Weather-predictor znode", e1)
        logging.error("Error while creating Weather-predictor znode %s" % str(e1))
    else:
        logging.debug("/weather-predictor znode created")

    try:  # create service znode
        zk.create('/weather-predictor/stormDetector')
    except Exception as e2:
        print("Error while creating /weather-predictor/stormDetector znode", e2)
        logging.error("Error while creating /weather-predictor/stormDetector znode %s" % str(e2))
    else:
        logging.debug("/weather-predictor/stormDetector znode created")

    zk.ensure_path("/weather-predictor/stormDetector")
    print(zk.client_id)

    try:
        uniqueid = str(uuid.uuid4())
        zk.create('/weather-predictor/stormDetector/' + uniqueid,
                  json.dumps({'name': serviceName, 'id': uniqueid, 'address': ipaddress, 'port': port,
                              'sslPort': None, 'payload': None,
                              'registrationTimeUTC': (datetime.utcnow() - datetime.utcfromtimestamp(0)).total_seconds(),
                              'serviceType': 'DYNAMIC',
                              "uriSpec": {
                                  "parts": [{"value": path, "variable": False}]
                              }}, ensure_ascii=True).encode(),
                  ephemeral=True)

    except Exception as e3:
        print("Error while creating weather-predictor/stormDetector znode", e3)
        logging.error("Error while creating /weather-predictor/stormDetector child znode %s" % str(e3))
    else:
        logging.debug("/weather-predictor/stormDetector child znode created %s" % uniqueid)
        # ******************REGISTERED************

#register
register_to_zookeeper()

#start service
app = Flask(__name__)

@app.route('/')
def test():
    return "Test service"

@app.route('/stormdetector/v1/service', methods=['POST'])
def sendkml():
    data=request.get_json()
    userName=data['userName']
    requestId=data['requestId']
    print(data,userName,requestId)
    kmldata=getkmlfile(2016,5,5,'Bloomington','sample.txt')
    #---------------------------------------------------------
    #connect to registry
    try:
        config = ConfigParser()
        config.read('config.ini')
        host1 = config.get('registryConfig', 'ipaddress1')
        port1 = config.get('registryConfig', 'port1')

        url1="http://"+host1+":"+port1+"/registry/v1/service/log"
        print(url1)
        log1={'userName':userName,'requestId':requestId,'serviceName':'Storm Detector','description':'success'}
        headers1 = {'Content-type': 'application/json'}

        r1 = requests.post(url1, data=json.dumps(log1,ensure_ascii=False), headers=headers1)
        print("registry response",r1.content)
    except:
        print("Couldn't connect to registry service")
    #---------------------------------------------------------

    data2 = {'userName': userName, 'requestId': requestId, 'data': kmldata}
    return json.dumps(data2,ensure_ascii=False)

def getkmlfile(yy,mm,dd,station,filename):
    return 'KML_output.kml'

if __name__ == '__main__':
    app.run(host='0.0.0.0',port=8000)