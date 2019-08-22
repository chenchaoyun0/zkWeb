FROM java:8

ENV TZ Asia/Shanghai

VOLUME /u01/app/db

WORKDIR /u01/app

ADD zkweb/zkWeb.war /u01/app

CMD ["java", "-jar", "zkWeb.war"]