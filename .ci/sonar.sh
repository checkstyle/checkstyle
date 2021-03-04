#!/bin/sh

# set checkstyle sonar profile
curl -X POST -u admin:admin \
    -F 'backup=@config/default_sonar_profile.xml' -v http://localhost:9000/api/profiles/restore

# execute inspection
mvn -e --no-transfer-progress sonar:sonar -P sonar -Dsonar.language=java \
  -Dsonar.profile=checkstyle-profile

# Uncomment following to get HTML report.
# mvn -e sonar:sonar -Dsonar.analysis.mode=preview -Dsonar.issuesReport.html.enable=true \
#        -Dsonar.language=java -Dsonar.profile=checkstyle-profile


# get and parse response from sonar
# give some time to sonar for report processing
sleep "60"
curl -u admin:admin \
   -v http://localhost:9000/api/issues/search?componentRoots=com.puppycrawl.tools:checkstyle \
        > response.json

OUTPUT="$(cat response.json | jq '.total')"

# print number of found issues
if [ ! "$OUTPUT" -eq "0" ]; then
    jq '.' response.json
    echo "Found issues - $OUTPUT"
fi
