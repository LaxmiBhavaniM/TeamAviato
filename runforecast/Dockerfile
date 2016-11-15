FROM python:3-onbuild

RUN pip install -r requirements.txt
EXPOSE 8050
ENTRYPOINT ["python"]
CMD ["runforecast.py"]