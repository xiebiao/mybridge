#!/bin/sh

CLASSPATH=$CLASSPATH:../libs/*:../conf/server.xml:../conf/log4j.properties

java -classpath $CLASSPATH com.github.mybridge.Mybridge
