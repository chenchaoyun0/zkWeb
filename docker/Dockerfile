FROM java:8

ENV TZ Asia/Shanghai

VOLUME /u01/app/db

WORKDIR /u01/app

COPY zkWeb/zkWeb.war /u01/app/zk-web.war

CMD ["java", "-jar", "zk-web.war"]