FROM nginx:1.19.2

RUN rm /etc/nginx/conf.d/default.conf
COPY docker/nginx/example.conf /etc/nginx/conf.d/

ADD ./docker/nginx/uwsgi_params /etc/nginx/

