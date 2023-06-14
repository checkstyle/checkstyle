#!/bin/bash

# Run "firefox target/site/linkcheck.html" after completion to review html report

set -e
pwd
uname -a
mvn --version
curl --fail-with-body -I https://sourceforge.net/projects/checkstyle/
mvn -e --no-transfer-progress clean site -Dcheckstyle.ant.skip=true -DskipTests -DskipITs \
   -Dpmd.skip=true -Dspotbugs.skip=true -Djacoco.skip=true -Dcheckstyle.skip=true
mkdir -p .ci-temp

echo "------------ grep of linkcheck.html--BEGIN"
# "grep ... | cat" is required command is running in "set -e" mode and
# grep could return exit code 1 if nothing is matching
grep -E "doesn't exist|externalLink" target/site/linkcheck.html | grep -v 'Read timed out' \
  | cat | sort > .ci-temp/linkcheck-errors-sorted.txt

sort config/linkcheck-suppressions.txt > .ci-temp/linkcheck-suppressions-sorted.txt

# Suppressions exist until https://github.com/checkstyle/checkstyle/issues/11572
diff .ci-temp/linkcheck-suppressions-sorted.txt .ci-temp/linkcheck-errors-sorted.txt || true
echo "------------ grep of linkcheck.html--END"

RESULT=$(diff -y --suppress-common-lines .ci-temp/linkcheck-suppressions-sorted.txt \
  .ci-temp/linkcheck-errors-sorted.txt | wc -l)

echo 'Exit code:'"$RESULT"
if [[ $RESULT != 0 ]]; then false; fi
