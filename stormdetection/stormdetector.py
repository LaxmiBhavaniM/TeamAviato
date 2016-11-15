from configparser import ConfigParser

import requests
from flask import Flask
from flask import Response
from flask import json
from flask import request

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

    #*********************************************************
    #connect to storm clustering
    config = ConfigParser()
    config.read('config.ini')
    host2 = config.get('configData', 'ipaddress2')
    port2=config.get('configData','port2')
    url2 = "http://" + host2 + ":" + port2 + "/stormclustering/v1/service/kml"
    print(url2)
    data2 = {'userName': userName, 'requestId': requestId, 'data':kmldata}
    #POST REQUEST
    headers2 = {'Content-type': 'application/json'}
    r2 = requests.post(url2, data=json.dumps(data2,ensure_ascii=False), headers=headers2)
    #*********************************************************

    #return r1
    print(r2.text)
    return r2.text
    #return Response(kmldata, mimetype='text/xml')

def getkmlfile(yy,mm,dd,station,filename):
    return 'KML_output.kml'

if __name__ == '__main__':
    app.run(host='0.0.0.0',port=8000)