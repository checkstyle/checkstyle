#!/bin/bash

# Run "firefox target/site/linkcheck.html" after completion to review html report

set -e
pwd
uname -a
mvn --version
curl --fail-with-body -I https://sourceforge.net/projects/checkstyle/

# Function to calculate the replacement string based on the file depth
calculate_replacement() {
    local file_path="$1"
    local relative_path="${file_path#src/main/java/}"
    local depth=$(echo "$relative_path" | grep -o "/" | wc -l)
    local replacement=""
    for ((i=0; i<depth; i++)); do
        if ((i > 0)); then
            replacement="/$replacement"
        fi
        replacement="..$replacement"
    done
    echo "../$replacement"
}

# convert all javadoc website links to relative links
grep -rl "https://checkstyle.org" src/main/java | while read -r file; do
    replacement=$(calculate_replacement "$file")
    sed -i "s|https://checkstyle.org|$replacement|g" "$file"
done

mvn -e --no-transfer-progress -Pno-validations clean site -Dlinkcheck.skip=false \
   -Dmaven.javadoc.skip=false

# revert all modified javadoc files for post validation
git checkout HEAD -- src/main/java

mkdir -p .ci-temp

OPTION=$1

echo "------------ grep of linkcheck.html--BEGIN"
LINKCHECK_ERRORS=$(grep -E "doesn't exist|externalLink" target/site/linkcheck.html \
  | grep -v 'Read timed out' | sed 's/<\/table><\/td><\/tr>//g' \
  | sed 's/<td><i>//g' | sed 's/<\/i><\/td><\/tr>//g' | sed 's/<\/table><\/section>//g' || true)

if [[ $OPTION == "--skip-external" ]]; then
  echo "Checking internal (checkstyle website) links only."
  echo "$LINKCHECK_ERRORS" | grep -v 'externalLink' | sort > .ci-temp/linkcheck-errors-sorted.txt
else
  echo "Checking internal (checkstyle website) and external links."
  echo "$LINKCHECK_ERRORS" | sort > .ci-temp/linkcheck-errors-sorted.txt
fi

sort config/linkcheck-suppressions.txt > .ci-temp/linkcheck-suppressions-sorted.txt

# Suppressions exist until https://github.com/checkstyle/checkstyle/issues/11572
diff -u .ci-temp/linkcheck-suppressions-sorted.txt .ci-temp/linkcheck-errors-sorted.txt || true
echo "------------ grep of linkcheck.html--END"

RESULT=$(diff -y --suppress-common-lines .ci-temp/linkcheck-suppressions-sorted.txt \
  .ci-temp/linkcheck-errors-sorted.txt | wc -l)
rm .ci-temp/linkcheck-suppressions-sorted.txt
rm .ci-temp/linkcheck-errors-sorted.txt

echo 'Exit code:'"$RESULT"
if [[ $RESULT != 0 ]]; then false; fi
