#!/bin/bash

set -e

uname -a
mvn --version
curl -I https://sourceforge.net/projects/checkstyle/
mvn clean site -Dcheckstyle.ant.skip=true -DskipTests -DskipITs -Dpmd.skip=true -Dfindbugs.skip=true -Dcobertura.skip=true -Dcheckstyle.skip=true
echo "------------ grep of linkcheck.html--BEGIN"
grep externalLink target/site/linkcheck.html | cat
echo "------------ grep of linkcheck.html--END"
RESULT=$(grep externalLink target/site/linkcheck.html | grep -v 'Read timed out' | wc -l)
echo 'Exit code:'$RESULT
if [[ $RESULT != 0 ]]; then false; fi