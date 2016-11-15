from unittest import TestCase
from stormclustering import app

class TestPerformclustering(TestCase):
    def test_performclstering(self):
        from stormclustering import performclustering
        self.assertTrue(type(performclustering('<xmldata>')) == dict)

    #component test case
    def test_stormclustering_endpoint(self):
        tester = app.test_client(self)
        response = tester.get('/')
        self.assertEqual(response.status_code, 200)

