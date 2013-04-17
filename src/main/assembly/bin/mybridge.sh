#!/bin/sh

cd ../
BASEDIR=`dirname $0`

CLASSPATH=$CLASSPATH:$BASEDIR/libs/*:$BASEDIR/conf/server.xml:$BASEDIR/conf/log4j.properties
java -classpath $CLASSPATH com.github.mybridge.MyBridge
