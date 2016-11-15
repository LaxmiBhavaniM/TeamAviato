FROM python:3-onbuild

RUN pip install -r requirements.txt
EXPOSE 8000
ENTRYPOINT ["python"]
CMD ["stormdetector.py"]