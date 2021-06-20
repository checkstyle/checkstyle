#!/bin/bash
set -e

if [ -z "$1" ]; then
    echo "No parameters supplied!"
    echo "      The classpath of the project and it's libraries to compile must be supplied."
    exit 1
fi

JAVA_RELEASE=${2:-1.8}

ECJ_MAVEN_VERSION="R-4.20-202106111600"
ECJ_JAR="ecj-4.20.jar"
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
echo "java -jar $ECJ_PATH -target ${JAVA_RELEASE} -source ${JAVA_RELEASE} -cp $1  ..."

set +e
java -jar $ECJ_PATH -target ${JAVA_RELEASE} -source ${JAVA_RELEASE} -encoding UTF-8 -cp $1 \
        -d target/eclipse-compile \
        -properties config/org.eclipse.jdt.core.prefs \
        -enableJavadoc \
        -nowarn:[target/generated-sources/antlr] \
        src/main/java \
        target/generated-sources/antlr \
        src/test/java \
        src/it/java \
    > $RESULT_FILE 2>&1
EXIT_CODE=$?
set -e

if [[ $EXIT_CODE != 0 ]]; then
  echo "Content of $RESULT_FILE:"
  cat $RESULT_FILE
  false
else
    # check compilation of resources, all WARN and INFO are ignored
    set +e
    java -jar $ECJ_PATH -target ${JAVA_RELEASE} -source ${JAVA_RELEASE} -cp $1 \
            -d target/eclipse-compile \
            -nowarn \
            src/main/java \
            src/test/java \
            target/generated-sources/antlr \
            src/test/resources \
            src/it/resources \
        > $RESULT_FILE 2>&1
    EXIT_CODE=$?
    set -e

    if [[ $EXIT_CODE != 0 ]]; then
      echo "Content of $RESULT_FILE:"
      cat $RESULT_FILE
      false
    fi
fi

