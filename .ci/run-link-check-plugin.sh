#!/bin/bash

# Run "firefox target/site/linkcheck.html" after completion to review html report

set -e
pwd
uname -a
mvn --version
curl -I https://sourceforge.net/projects/checkstyle/
mvn -e --no-transfer-progress clean site -Dcheckstyle.ant.skip=true -DskipTests -DskipITs \
   -Dpmd.skip=true -Dspotbugs.skip=true -Djacoco.skip=true -Dcheckstyle.skip=true
echo "------------ grep of linkcheck.html--BEGIN"
# "grep ... | cat" is required command is running in "set -e" mode and
# grep could return exit code 1 if nothing is matching
grep externalLink target/site/linkcheck.html | cat
echo "------------ grep of linkcheck.html--END"
RESULT=$(grep externalLink target/site/linkcheck.html | grep -v 'Read timed out' | wc -l)
echo 'Exit code:'$RESULT
if [[ $RESULT != 0 ]]; then false; fi

