#!/bin/bash
cd ProfileData
mvn clean install -DskipTests
if [ "$?" -ne "0" ] 
then 
    exit
fi
cd ../Profiler
mvn clean install -DskipTests
if [ "$?" -ne "0" ]
then
    exit
fi
cd ../TestProject
mvn clean install -DskipTests

