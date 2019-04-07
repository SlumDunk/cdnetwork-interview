#!/bin/bash
export JRE_HOME=/${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/jre/lib/rt.jar:${JAVA_HOME}/lib/dt.jar:${JAVA_HOME}/lib/tools.jar
export PATH=$PATH:${JAVA_HOME}/bin:${JRE_HOME}/bin

APP_NAME=./cdn-log-queryer-jar-with-dependencies.jar
JAVA_OPTS="-ms512m -mx512m -Xmn256m"


java ${JAVA_OPTS} -jar ${APP_NAME} $1