from configparser import ConfigParser

from flask import Flask
from flask import Response
from flask import json
from flask import request
import requests
import random
import urllib.request

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

    try:
        host = urllib.request.urlopen("http://169.254.169.254/latest/meta-data/public-ipv4").read().decode('utf-8')
    except Exception as e:
        print("Error while connecting to aws get instance info",type(e))
        host="localhost"

    zport=2181
    zurl = host + ":" + str(zport)
    zk = KazooClient(hosts=zurl)
    # zk = KazooClient(hosts='localhost:2181')
    zk.start()

    # ********** register service with zookeeper *********
    serviceName = "runForecast"
    ipaddress = host
    serviceURI = "/runforecast/v1/service"
    port = 8050
    path = "http://" + ipaddress + ":" + str(port) + serviceURI

    try:  # create base
        zk.create('/weather-predictor')
    except Exception as e1:
        print("Error while creating Weather-predictor znode", type(e1))
        logging.error("Error while creating Weather-predictor znode %s" % type(e1))
    else:
        logging.debug("/weather-predictor znode created")

    try:  # create service znode
        zk.create('/weather-predictor/runForecast')
    except Exception as e2:
        print("Error while creating /weather-predictor/runForecast znode", type(e2))
        logging.error("Error while creating /weather-predictor/runForecast znode %s" % type(e2))
    else:
        logging.debug("/weather-predictor/runForecast znode created")

    zk.ensure_path("/weather-predictor/runForecast")
    print(zk.client_id)

    try:
        uniqueid = str(uuid.uuid4())
        zk.create('/weather-predictor/runForecast/' + uniqueid,
                  json.dumps({'name': serviceName, 'id': uniqueid, 'address': ipaddress, 'port': port,
                              'sslPort': None, 'payload': None,
                              'registrationTimeUTC': (datetime.utcnow() - datetime.utcfromtimestamp(0)).total_seconds(),
                              'serviceType': 'DYNAMIC',
                              "uriSpec": {
                                  "parts": [{"value": path, "variable": False}]
                              }}, ensure_ascii=True).encode(),
                  ephemeral=True)

    except Exception as e3:
        print("Error while creating weather-predictor/runForecast znode", type(e3))
        logging.error("Error while creating /weather-predictor/runForecast child znode %s" % type(e3))
    else:
        logging.debug("/weather-predictor/runForecast child znode created %s" % uniqueid)
        # ******************REGISTERED************

#register
register_to_zookeeper()

app = Flask(__name__)

@app.route('/')
def test():
    return "Test service"

@app.route('/runforecast/v1/service',methods=['POST'])
def generatecluster():
    request_data = request.get_json()
    userName = request_data['userName']
    requestId = request_data['requestId']
    cluster={'cluster1':12345}
    forecast=get_forecast(cluster)

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
        log1 = {'userName': userName, 'requestId': requestId, 'serviceName': 'Run Forecast', 'description': 'success'}
        headers1 = {'Content-type': 'application/json'}

        r1 = requests.post(url1, data=json.dumps(log1, ensure_ascii=False), headers=headers1)
        print("registry response", r1.content)
    except:
        print("Couldn't connect to registry service.")
    # ---------------------------------------------------------

    return Response(json.dumps(forecast), mimetype='application/json')


def get_forecast(cluster):
    weatherType=random.choice(['heavy rains', 'sunny', 'cloudy', 'thunderstorms', 'clear skies'])
    temperature = random.randint(0,150)
    units = 'degrees Farenheit'

    return {'weatherType':weatherType,'temperature':temperature,'units':units}
if __name__ == '__main__':
    app.run(host='0.0.0.0',port=8050)