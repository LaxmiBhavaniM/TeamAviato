import unittest
from runforecast import app
import json

class FlaskTestCase(unittest.TestCase):
    #test runforecast endpoint
    def test_runforecast_endpoint(self):
        tester = app.test_client(self)
        response = tester.post('/runforecast/v1/service',data=json.dumps(dict(requestId = "10230",userName="fdhsjgdkf")),
                       content_type='application/json')
        self.assertEqual(response.status_code,200)
#component testcase
class TestGet_forecast(unittest.TestCase):
    def test_get_forecast(self):
        from runforecast import get_forecast
        self.assertTrue(type(get_forecast({'cluster1': 1000, 'cluster2': 2000})) == dict)


