#!/bin/sh -e

ECJ_JAR="ecj-4.7.jar"
ECJ_MAVEN_VERSION="R-4.7-201706120950"

if [ ! -f ~/.m2/repository/$ECJ_MAVEN_VERSION/$ECJ_JAR ]; then
    wget http://ftp-stud.fht-esslingen.de/pub/Mirrors/eclipse/eclipse/downloads/drops4/$ECJ_MAVEN_VERSION/$ECJ_JAR -P ~/.m2/repository/$ECJ_MAVEN_VERSION
fi

mkdir -p target/classes
mkdir -p target/eclipse

set +e
java -jar ~/.m2/repository/$ECJ_MAVEN_VERSION/$ECJ_JAR -target 1.8 -source 1.8 -cp $1 \
        -nowarn:[./target/generated-sources/antlr] -d target/eclipse-compile \
        -enableJavadoc src/main/java target/generated-sources/antlr -properties config/org.eclipse.jdt.core.prefs \
    > target/eclipse/report.txt 2>&1
set -e

if (( $(grep ERROR target/eclipse/report.txt | cat | wc -l) > 0 )); then
  cat target/eclipse/report.txt
  false
fi
