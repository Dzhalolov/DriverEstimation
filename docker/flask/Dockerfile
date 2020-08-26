FROM python:3.8-alpine

RUN adduser -D user

WORKDIR /home/user
COPY requirements.txt requirements.txt


RUN apk update && apk add postgresql-dev gcc libc-dev git make libffi-dev openssl-dev python3-dev libxml2-dev libxslt-dev \ 
     &&  pip3 install --no-cache-dir  -r requirements.txt && pip install uWSGI  
      
     


COPY ./docker/uwsgi/uwsgi.ini /etc/
COPY driverestimation driverestimation
COPY run.py boot.sh ./
RUN chmod +x boot.sh  && chown -R user:user ./
 
USER user

EXPOSE 5000
CMD ["./boot.sh"]
