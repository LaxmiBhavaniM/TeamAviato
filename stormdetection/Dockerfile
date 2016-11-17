FROM python:3-onbuild

RUN apt-get update && apt-get install -y supervisor
RUN apt-get update && apt-get install -y supervisor openssh-client
ADD ./configs/supervisord.conf /etc/supervisord.conf

RUN pip install -r requirements.txt
EXPOSE 8000

#ENTRYPOINT ["python"]
#CMD ["stormdetector.py","stormDetector_init.py"]

ENTRYPOINT ["/usr/bin/supervisord", "-c", "/etc/myapp/supervisord.conf"]