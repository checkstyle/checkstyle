#!/bin/bash
set -e

if [ -z "$1" ]; then
    echo "No parameters supplied!"
    echo "      The classpath of the project and it's libraries to compile must be supplied."
    exit 1
fi

ECJ_JAR="ecj-4.7.jar"
ECJ_MAVEN_VERSION="R-4.7-201706120950"
ECJ_PATH=~/.m2/repository/$ECJ_MAVEN_VERSION/$ECJ_JAR

if [ ! -f $ECJ_PATH ]; then
    echo "$ECJ_PATH is not found, downloading ..."
    mkdir -p $(dirname "$ECJ_PATH")
    ECLIPSE_URL="http://ftp-stud.fht-esslingen.de/pub/Mirrors/eclipse/eclipse/downloads/drops4"
    wget $ECLIPSE_URL/$ECJ_MAVEN_VERSION/$ECJ_JAR -O $ECJ_PATH
fi

mkdir -p target/classes target/test-classes target/eclipse

RESULT_FILE=target/eclipse/report.txt

echo "Executing eclipse compiler, output is redirected to $RESULT_FILE..."
echo "java -jar $ECJ_PATH -target 1.8 -source 1.8 -cp $1  ..."
"
java -jar $ECJ_PATH -target 1.8 -source 1.8 -cp $1 \
        -d target/eclipse-compile \
        -enableJavadoc \
        -nowarn:[./target/generated-sources/antlr] \
        -noerr:[./src/test/resources] \
        -nowarn:[./src/test/resources] \
        -noinfo:[./src/test/resources] \
        src/main/java \
        target/generated-sources/antlr \
        src/test/java \
        src/it/java \
        src/test/resources \
        src/it/resources \
        -properties config/org.eclipse.jdt.core.prefs \
    > $RESULT_FILE 2>&1 | true

echo "Checking for ERROR|WARNING|INFO  in $RESULT_FILE ..."
if [[ $(grep -E "ERROR|WARNING|INFO" $RESULT_FILE | cat | wc -l) > 0 ]]; then
  cat $RESULT_FILE
  false
fi
