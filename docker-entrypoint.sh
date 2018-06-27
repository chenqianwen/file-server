#!/bin/sh
if [ -n "$ENV" ] ; then
PROFILES_ACTIVE=$ENV
else
PROFILES_ACTIVE="dev"
fi
echo '环境---------------'
echo ${PROFILES_ACTIVE}
java -jar /home/server.jar --server.port=8080 $JAVA_OPTS