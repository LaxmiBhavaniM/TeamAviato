import logging
import urllib.request


logging.basicConfig(filename='dummyfile.log', level=logging.DEBUG, format="%(asctime)s - %(name)s - %(message)s",
                        datefmt="%H:%M:%S", filemode='w')
logging.debug("my public ip "+str(urllib.request.urlopen("curl http://169.254.169.254/latest/meta-data/public-ipv4").read()))
