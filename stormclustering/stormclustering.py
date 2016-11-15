from configparser import ConfigParser

from flask import Flask
from flask import Response
from flask import json
from flask import request
import requests
from logging import FileHandler, WARNING

app = Flask(__name__)
if not app.debug:
    file_handler = FileHandler('errorlog.txt')
    file_handler.setLevel(WARNING)
    app.logger.addHandler(file_handler)

@app.route('/')
def test():
    return "Test service"

@app.route('/stormclustering/v1/service/kml', methods=['POST'])
def generatecluster():
    request_data = request.get_json()
    userName=request_data['userName']
    requestId=request_data['requestId']
    kmldata=request_data['data']

    cluster = performclustering(kmldata)

    # ---------------------------------------------------------
    # connect to registry
    try:
        config = ConfigParser()
        config.read('config.ini')
        host1 = config.get('registryConfig', 'ipaddress1')
        port1 = config.get('registryConfig', 'port1')

        url1 = "http://" + host1 + ":" + port1 + "/registry/v1/service/log"
        print(url1)
        log1 = {'userName': userName, 'requestId': requestId, 'serviceName': 'Storm Clustering', 'description': 'success'}
        headers1 = {'Content-type': 'application/json'}

        r1 = requests.post(url1, data=json.dumps(log1, ensure_ascii=False), headers=headers1)
        print("registry response", r1.content)

    except:
        print("Couldn't connect to registry service.")
    # ---------------------------------------------------------

    # *********************************************************
    # connect to forecast trigger
    config = ConfigParser()
    config.read('config.ini')
    host2 = config.get('configData', 'ipaddress2')
    port2 = config.get('configData', 'port2')
    url2 = "http://" + host2 + ":" + port2 + "/forecasttrigger/v1/service/trigger"
    print(url2)
    data2 = {'userName': userName, 'requestId': requestId, 'data': cluster}
    # POST REQUEST
    headers2 = {'Content-type': 'application/json'}
    r2 = requests.post(url2, data=json.dumps(data2, ensure_ascii=False), headers=headers2)
    # *********************************************************

    return r2.text
    #return json.dumps({'sample':12553})


def performclustering(kmldata):
    dummy_cluster = {
        'Cluster0': 186.7885,
        'Cluster1': 110.7402,
        'Cluster2': 29.8850,
        'Cluster3': 55.5580,
        'Cluster4': 212.9605
    }
    return dummy_cluster


if __name__ == '__main__':
    app.run(host='0.0.0.0',port=31000)
