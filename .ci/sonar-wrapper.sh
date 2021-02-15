#!/bin/sh

# This file is for manual execution only

set -e

echo "Building docker image"
docker pull sonarqube
echo "Running docker container"
docker run -dit -p 9000:9000 --name sonar sonarqube:latest
echo "sleeping 60 sec to let sonar start up"
sleep "60"

./.ci/sonar.sh

# kill container
docker stop sonar
docker rm sonar
