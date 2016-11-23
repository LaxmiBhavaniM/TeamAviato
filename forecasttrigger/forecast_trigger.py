from configparser import ConfigParser

import requests
from flask import Flask
from flask import Response
from flask import json
from flask import request
import random

import json
import calendar, datetime, time
from datetime import datetime
from kazoo.client import KazooClient
from kazoo.client import KazooState
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

def my_listener(state):
    if state==KazooState.LOST:
        register_to_zookeeper()
        print("Connected")
    elif state==KazooState.SUSPENDED:
        print ("connection suspended")
    else:
        print(state)
        print("connection error")

def get_host_url():
    try:
        host = urllib.request.urlopen("http://169.254.169.254/latest/meta-data/public-ipv4").read().decode('utf-8')
    except Exception as e:
        print("Error while connecting to aws get instance info",type(e))
        host="localhost"
    return host

def register_to_zookeeper():
    logging.basicConfig(filename='zkregistry.log', level=logging.DEBUG, format="%(asctime)s - %(name)s - %(message)s",
                        datefmt="%H:%M:%S", filemode='w')
    host = get_host_url()
    zport = 2181
    zurl = host + ":" + str(zport)
    zk = KazooClient(hosts=zurl)
    # zk = KazooClient(hosts='localhost:2181')
    zk.start()
    zk.add_listener(my_listener)


    # ********** register service with zookeeper *********
    serviceName = "triggerForecast"
    ipaddress = host
    serviceURI = "/forecasttrigger/v1/service/trigger"
    port = 32000
    path = "http://" + ipaddress + ":" + str(port) + serviceURI

    try:  # create base
        zk.create('/weather-predictor')
    except Exception as e1:
        print("Error while creating Weather-predictor znode", type(e1))
        logging.error("Error while creating Weather-predictor znode %s" % type(e1))
    else:
        logging.debug("/weather-predictor znode created")

    try:  # create service znode
        zk.create('/weather-predictor/triggerForecast')
    except Exception as e2:
        print("Error while creating /weather-predictor/triggerForecast znode", type(e2))
        logging.error("Error while creating /weather-predictor/triggerForecast znode %s" % type(e2))
    else:
        logging.debug("/weather-predictor/triggerForecast znode created")

    zk.ensure_path("/weather-predictor/triggerForecast")
    print(zk.client_id)

    try:
        uniqueid = str(uuid.uuid4())
        zk.create('/weather-predictor/triggerForecast/' + uniqueid,
                  json.dumps({'name': serviceName, 'id': uniqueid, 'address': ipaddress, 'port': port,
                              'sslPort': None, 'payload': None,
                              'registrationTimeUTC': (datetime.utcnow() - datetime.utcfromtimestamp(0)).total_seconds(),
                              'serviceType': 'DYNAMIC',
                              "uriSpec": {
                                  "parts": [{"value": path, "variable": False}]
                              }}, ensure_ascii=True).encode(),
                  ephemeral=True)

    except Exception as e3:
        print("Error while creating weather-predictor/triggerForecast znode", type(e3))
        logging.error("Error while creating /weather-predictor/triggerForecast child znode %s" % type(e3))
    else:
        logging.debug("/weather-predictor/triggerForecast child znode created %s" % uniqueid)
        # ******************REGISTERED************

#start service
app = Flask(__name__)

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
        try:
            host = urllib.request.urlopen("http://169.254.169.254/latest/meta-data/public-ipv4").read().decode('utf-8')
        except Exception as e:
            print("Error while connecting to aws get instance info", type(e))
            host = "localhost"

        config = ConfigParser()
        config.read('config.ini')
        host1 = host
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
    # register
    register_to_zookeeper()
    app.run(host='0.0.0.0',port=32000)