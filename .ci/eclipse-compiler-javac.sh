#!/bin/bash
set -e

if [ -z "$1" ]; then
    echo "No parameters supplied!"
    echo "      The classpath of the project and it's libraries to compile must be supplied."
    exit 1
fi

ECJ_JAR="ecj-4.7.2.jar"
ECJ_MAVEN_VERSION="R-4.7.2-201711300510"
ECJ_PATH=~/.m2/repository/$ECJ_MAVEN_VERSION/$ECJ_JAR

if [ ! -f $ECJ_PATH ]; then
    echo "$ECJ_PATH is not found, downloading ..."
    mkdir -p $(dirname "$ECJ_PATH")
    wget http://ftp-stud.fht-esslingen.de/pub/Mirrors/eclipse/eclipse/downloads/drops4/$ECJ_MAVEN_VERSION/$ECJ_JAR -O $ECJ_PATH
fi

mkdir -p target/classes
mkdir -p target/eclipse

RESULT_FILE=target/eclipse/report.txt

echo "Executing eclipse compiler, output is redirected to $RESULT_FILE..."
java -jar $ECJ_PATH -target 9 -source 9 -cp $1 \
        -nowarn:[./target/generated-sources/antlr] -d target/eclipse-compile \
        -enableJavadoc src/main/java src/test/java target/generated-sources/antlr -properties config/org.eclipse.jdt.core.prefs \
    > $RESULT_FILE 2>&1 | true

echo "Checking for ERROR|WARNING|INFO  in $RESULT_FILE ..."
if [[ $(grep -E "ERROR|WARNING|INFO" $RESULT_FILE | cat | wc -l) > 0 ]]; then
  cat $RESULT_FILE
  false
fi
