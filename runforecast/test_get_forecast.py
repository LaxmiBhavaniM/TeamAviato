from unittest import TestCase


class TestGet_forecast(TestCase):
    def test_get_forecast(self):
        from runforecast import get_forecast
        self.assertTrue(type(get_forecast({'cluster1':1000,'cluster2':2000})) == dict)
