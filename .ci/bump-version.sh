#!/bin/bash
set -e

VERSION=$1
echo VERSION="$VERSION"

echo "bump version in pom.xml"
mvn -e --no-transfer-progress versions:set -DnewVersion="$VERSION-SNAPSHOT"
mvn -e --no-transfer-progress versions:commit
