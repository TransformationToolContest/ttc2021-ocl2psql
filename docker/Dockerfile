FROM mysql:5.7

MAINTAINER Hoang Nguyen "ngpbhoang1406@gmail.com"

ENV MYSQL_ROOT_PASSWORD=password

ADD init.sql /docker-entrypoint-initdb.d

EXPOSE 3306