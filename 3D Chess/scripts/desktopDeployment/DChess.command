#!/bin/bash
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
cd "${DIR}"
./jre/Contents/Home/bin/java -XX:MaxRAMPercentage=60 -XstartOnFirstThread -classpath "lib/*" edu.neumont.csc.DChess
exit 0
