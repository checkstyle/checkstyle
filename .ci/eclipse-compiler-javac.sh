#!/bin/sh
set -e

ECJ_VERSION="4.7"
mkdir -p target/classes
mkdir -p target/eclipse

if [ ! -e ~/.m2/repository/ecj-$ECJ_VERSION.jar ]; then
    wget http://ftp-stud.fht-esslingen.de/pub/Mirrors/eclipse/eclipse/downloads/drops4/R-4.7-201706120950/"ecj-$ECJ_VERSION.jar" -P ~/.m2/repository/
fi

java -jar ~/.m2/repository/ecj-$ECJ_VERSION.jar -target 1.8 -source 1.8 -cp $1 \
        -nowarn:[./target/generated-sources/antlr] -d target/eclipse-compile \
        -enableJavadoc src/main/java target/generated-sources/antlr -properties config/org.eclipse.jdt.core.prefs \
    \
    > target/eclipse/report.txt 2>&1
