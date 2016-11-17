FROM python:3-onbuild

RUN apt-get update && apt-get install -y supervisor
RUN apt-get update && apt-get install -y supervisor openssh-client
ADD ./configs/supervisord.conf /etc/supervisord.conf

RUN pip install -r requirements.txt

COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf
EXPOSE 8000

CMD ["/usr/bin/supervisord"]

#ENTRYPOINT ["python"]
#CMD ["stormdetector.py","stormDetector_init.py"]

