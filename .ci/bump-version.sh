#!/bin/bash
set -e

VERSION=$1
echo VERSION="$VERSION"

echo "bump version in pom.xml"
mvn versions:set -DnewVersion="$VERSION" && mvn versions:commit
