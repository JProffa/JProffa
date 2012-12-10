#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $DIR

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
cd ../Graph
mvn clean install -DskipTests
if [ "$?" -ne "0" ]
then
    exit
fi
cd ../TestProject
mvn clean install -DskipTests

