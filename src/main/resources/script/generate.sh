#!/bin/bash
export JRE_HOME=/${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/jre/lib/rt.jar:${JAVA_HOME}/lib/dt.jar:${JAVA_HOME}/lib/tools.jar
export PATH=$PATH:${JAVA_HOME}/bin:${JRE_HOME}/bin
JAVA_OPTS="-ms512m -mx512m -Xmn256m"

APP_NAME=./cdn-log-generator-jar-with-dependencies.jar

java ${JAVA_OPTS} -jar ${APP_NAME} $1 $2

