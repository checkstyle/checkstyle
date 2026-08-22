#!/bin/bash
# Checks HTML links in the generated site using lychee-maven-plugin.

set -e

echo "========================================="
echo "Link Check Plugin"
echo "========================================="

pwd
uname -a
./mvnw --version

echo "========================================="
echo "Building site and checking links..."
echo "========================================="

OPTION=$1
if [[ $OPTION == "--skip-external" ]]; then
  echo "Checking internal (checkstyle website) links only."
  ./mvnw -e --no-transfer-progress clean post-site -Pno-validations \
    -Dmaven.javadoc.skip=false -Dlychee.skip=false \
    -Plychee-offline
else
  echo "Checking internal (checkstyle website) and external links."
  curl --fail-with-body -I https://sourceforge.net/projects/checkstyle/
  export GITHUB_TOKEN="${GITHUB_TOKEN:-}"
  ./mvnw -e --no-transfer-progress clean post-site -Pno-validations \
    -Dmaven.javadoc.skip=false -Dlychee.skip=false
fi

echo "========================================="
echo "Link check completed successfully!"
echo "========================================="
