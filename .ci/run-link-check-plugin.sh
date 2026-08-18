#!/bin/bash
# Checks HTML links in the generated site using lychee-maven-plugin.

set -e

echo "========================================="
echo "Link Check Plugin"
echo "========================================="

pwd
uname -a
./mvnw --version
curl --fail-with-body -I https://sourceforge.net/projects/checkstyle/

echo "========================================="
echo "Building site and checking links..."
echo "========================================="

OPTION=$1
if [[ $OPTION == "--skip-external" ]]; then
  echo "Checking internal (checkstyle website) links only."
  ./mvnw -e --no-transfer-progress clean site post-site \
    -Dcheckstyle.ant.skip=true -DskipTests -DskipITs \
    -Dcheckstyle.skipCompileInputResources=true \
    -Dpmd.skip=true -Dspotbugs.skip=true -Djacoco.skip=true \
    -Dcheckstyle.skip=true \
    -Plychee-offline
else
  echo "Checking internal (checkstyle website) and external links."
  ./mvnw -e --no-transfer-progress clean site post-site \
    -Dcheckstyle.ant.skip=true -DskipTests -DskipITs \
    -Dcheckstyle.skipCompileInputResources=true \
    -Dpmd.skip=true -Dspotbugs.skip=true -Djacoco.skip=true \
    -Dcheckstyle.skip=true
fi

echo "========================================="
echo "Link check completed successfully!"
echo "========================================="
