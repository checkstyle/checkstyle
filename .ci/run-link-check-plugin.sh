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
LINKCHECK_ERRORS=$(grep -E "doesn't exist|externalLink" target/site/linkcheck.html \
  | grep -v 'Read timed out' | sed 's/<\/table><\/td><\/tr>//g' || true)

BRANCH=$(git symbolic-ref --short HEAD)
if [[ $BRANCH == "master" ]]; then
  echo "Branch is master, so we will check for external links."
  echo "$LINKCHECK_ERRORS" | sort > .ci-temp/linkcheck-errors-sorted.txt
  exit 0
else
  echo "Branch is not master, so we will not check for external links."
  echo "$LINKCHECK_ERRORS" | grep -v 'externalLink' | sort > .ci-temp/linkcheck-errors-sorted.txt
fi

sort config/linkcheck-suppressions.txt | sed 's/<\/table><\/td><\/tr>//g' \
  > .ci-temp/linkcheck-suppressions-sorted.txt

# Suppressions exist until https://github.com/checkstyle/checkstyle/issues/11572
diff .ci-temp/linkcheck-suppressions-sorted.txt .ci-temp/linkcheck-errors-sorted.txt || true
echo "------------ grep of linkcheck.html--END"

RESULT=$(diff -y --suppress-common-lines .ci-temp/linkcheck-suppressions-sorted.txt \
  .ci-temp/linkcheck-errors-sorted.txt | wc -l)
rm .ci-temp/linkcheck-suppressions-sorted.txt
rm .ci-temp/linkcheck-errors-sorted.txt

echo 'Exit code:'"$RESULT"
if [[ $RESULT != 0 ]]; then false; fi
