#!/usr/bin/env bash

set -e

CURR_YEAR=$(date +"%Y")
JAVA_FILE=$(find ./src/main -type f -name *.java -print -quit)
echo "Current year will be taken from $JAVA_FILE"
PREV_YEAR=$(grep "Copyright" $JAVA_FILE | cut -d " " -f 4 | cut -d '-' -f 2)
echo "CURR_YEAR=$CURR_YEAR"
echo "PREV_YEAR=$PREV_YEAR"

./.ci/bump-license-year.sh $PREV_YEAR $CURR_YEAR .
git add . && git commit -m "minor: bump year to $CURR_YEAR" && git push origin master

mkdir -p .ci-temp/bump-year
cd .ci-temp/bump-year

git clone git@github.com:checkstyle/contribution.git
git clone git@github.com:checkstyle/sonar-checkstyle.git
git clone git@github.com:checkstyle/regression-tool.git
git clone git@github.com:sevntu-checkstyle/sevntu.checkstyle.git
git clone git@github.com:sevntu-checkstyle/methods-distance.git

./../../.ci/bump-license-year.sh $PREV_YEAR $CURR_YEAR contribution
./../../.ci/bump-license-year.sh $PREV_YEAR $CURR_YEAR sonar-checkstyle
./../../.ci/bump-license-year.sh $PREV_YEAR $CURR_YEAR regression-tool
./../../.ci/bump-license-year.sh $PREV_YEAR $CURR_YEAR sevntu.checkstyle
./../../.ci/bump-license-year.sh $PREV_YEAR $CURR_YEAR methods-distance

cd contribution
git add . && git commit -m "minor: bump year to $CURR_YEAR" && git push origin master
cd ../
cd sonar-checkstyle
git add . && git commit -m "minor: bump year to $CURR_YEAR" && git push origin master
cd ../
cd regression-tool
git add . && git commit -m "minor: bump year to $CURR_YEAR" && git push origin master
cd ../
cd sevntu.checkstyle
git add . && git commit -m "minor: bump year to $CURR_YEAR" && git push origin master
cd ../
cd methods-distance
git add . && git commit -m "minor: bump year to $CURR_YEAR" && git push origin master
cd ../
