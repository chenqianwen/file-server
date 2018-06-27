FROM openjdk:8-jre
EXPOSE 8080
ADD ./target/demo-1.0.jar /home/server.jar
ENV TZ "Asia/Shanghai"
COPY docker-entrypoint.sh /usr/local/bin/
ENTRYPOINT ["sh","/usr/local/bin/docker-entrypoint.sh"]