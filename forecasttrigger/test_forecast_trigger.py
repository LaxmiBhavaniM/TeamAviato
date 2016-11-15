import unittest

from flask import json

from forecast_trigger import app

class Test_ForecastTrigger(unittest.TestCase):
    #test runforecast endpoint
    def test_main_endpoint(self):
        tester = app.test_client(self)
        response = tester.post('/forecasttrigger/v1/service/trigger',
                               data=json.dumps(dict(
                                   requestId = "10230",
                                   userName="fdhsjgdkf",
                                   data={
                                       'Cluster0': 186.7885,
                                       'Cluster1': 110.7402,
                                       'Cluster2': 29.8850,
                                       'Cluster3': 55.5580,
                                       'Cluster4': 212.9605
                                   })),
                               content_type='application/json')
        self.assertEqual(response.status_code,200)
    #component testcase
    def test_forecasttrigger_endpoint(self):
        tester = app.test_client(self)
        response = tester.get('/')
        self.assertEqual(response.status_code, 200)