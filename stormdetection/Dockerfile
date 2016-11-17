FROM python:3-onbuild

RUN apt-get update && apt-get install -y openssh-server apache2 supervisor
RUN mkdir -p /var/lock/apache2 /var/run/apache2 /var/run/sshd /var/log/supervisor

COPY ./configs/supervisord.conf /etc/supervisor/conf.d/supervisord.conf

RUN pip install -r requirements.txt
EXPOSE 8000

#ENTRYPOINT ["python"]
#CMD ["stormdetector.py","stormDetector_init.py"]

CMD ["/usr/bin/supervisord"]