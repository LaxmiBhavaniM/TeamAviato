from configparser import ConfigParser

import requests
from flask import Flask
from flask import Response
from flask import json
from flask import request
import random
app = Flask(__name__)

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

def register_to_zookeeper():
    logging.basicConfig(filename='zkregistry.log', level=logging.DEBUG, format="%(asctime)s - %(name)s - %(message)s",
                        datefmt="%H:%M:%S", filemode='w')

    zk = KazooClient(hosts='52.15.57.97:2181')
    # zk = KazooClient(hosts='localhost:2181')
    zk.start()
    # ********** register service with zookeeper *********
    serviceName = "forecastTrigger"
    ipaddress = "ec2-35-164-24-104.us-west-2.compute.amazonaws.com"
    serviceURI = "/forecasttrigger/v1/service/trigger"
    port = 32000
    path = "http://" + ipaddress + ":" + str(port) + ":" + serviceName

    try:  # create base
        zk.create('/weather-predictor')
    except Exception as e1:
        print("Error while creating Weather-predictor znode", e1)
        logging.error("Error while creating Weather-predictor znode %s" % str(e1))
    else:
        logging.debug("/weather-predictor znode created")

    try:  # create service znode
        zk.create('/weather-predictor/forecastTrigger')
    except Exception as e2:
        print("Error while creating /weather-predictor/forecastTrigger znode", e2)
        logging.error("Error while creating /weather-predictor/forecastTrigger znode %s" % str(e2))
    else:
        logging.debug("/weather-predictor/forecastTrigger znode created")

    zk.ensure_path("/weather-predictor/forecastTrigger")
    print(zk.client_id)

    try:
        uniqueid = str(uuid.uuid4())
        zk.create('/weather-predictor/forecastTrigger/' + uniqueid,
                  json.dumps({'name': serviceName, 'id': uniqueid, 'address': ipaddress, 'port': port,
                              'sslPort': None, 'payload': None,
                              'registrationTimeUTC': (datetime.utcnow() - datetime.utcfromtimestamp(0)).total_seconds(),
                              'serviceType': 'DYNAMIC',
                              "uriSpec": {
                                  "parts": [{"value": path, "variable": False}]
                              }}, ensure_ascii=True).encode(),
                  ephemeral=True)

    except Exception as e3:
        print("Error while creating weather-predictor/forecastTrigger znode", e3)
        logging.error("Error while creating /weather-predictor/forecastTrigger child znode %s" % str(e3))
    else:
        logging.debug("/weather-predictor/forecastTrigger child znode created %s" % uniqueid)
        # ******************REGISTERED************

#register
register_to_zookeeper()


@app.route('/')
def test():
    return "Test service"

@app.route('/forecasttrigger/v1/service/trigger',methods=['POST'])
def trigger_forecast():
    request_data = request.get_json()
    userName = request_data['userName']
    requestId = request_data['requestId']
    cluster = request_data['data']

    trigger_response=random.choice(["Yes","No"])

    # ---------------------------------------------------------
    # connect to registry
    try:
        config = ConfigParser()
        config.read('config.ini')
        host1 = config.get('registryConfig', 'ipaddress1')
        port1 = config.get('registryConfig', 'port1')

        url1 = "http://" + host1 + ":" + port1 + "/registry/v1/service/log"
        print(url1)
        log1 = {'userName': userName, 'requestId': requestId, 'serviceName': 'Forecast Trigger', 'description': 'success'}
        headers1 = {'Content-type': 'application/json'}

        r1 = requests.post(url1, data=json.dumps(log1, ensure_ascii=False), headers=headers1)
        print("registry response", r1.content)
    except:
        print("Couldn't connect to registry service.")
    # ---------------------------------------------------------


    return json.dumps({'trigger_response':trigger_response})

if __name__ == '__main__':
    app.run(host='0.0.0.0',port=32000)