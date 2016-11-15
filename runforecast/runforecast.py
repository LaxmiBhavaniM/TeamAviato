from configparser import ConfigParser

from flask import Flask
from flask import Response
from flask import json
from flask import request
import requests
import random
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
        config = ConfigParser()
        config.read('config.ini')
        host1 = config.get('registryConfig', 'ipaddress1')
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