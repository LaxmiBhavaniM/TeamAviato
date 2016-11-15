import unittest
from stormdetector import app
import json
class TestSendkml(unittest.TestCase):

    '''def test_sendkml(self):
        from stormdetector import sendkml
        self.assertTrue(type(sendkml())==str)
        #self.fail()'''

    def test_getkml(self):
        from stormdetector import getkmlfile
        self.assertTrue(type(getkmlfile(12,12,12,'stationA','stationA.txt'))==str)

    '''def test_connection(self):
        from stormdetector import app
        connect=app.test_client(self)
        response=connect.get("/stormdetector/v1/service/noaa-nexrad-level2.s3.amazonaws.com/03/12/12/ST/abc.txt")
        self.assertEqual(response.status_code, 200)'''

#component test
class FlaskTestCase(unittest.TestCase):
    # test runforecast endpoint
    def test_stormdetector_endpoint(self):
        tester = app.test_client(self)
        response = tester.get('/')
        self.assertEqual(response.status_code, 200)


