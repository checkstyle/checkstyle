

<<<<<<< refs/remotes/origin/master
#This script is used at https://app.codeship.com/projects/124310
#Run "firefox target/site/linkcheck.html" after completion to review html report

=======
>>>>>>> config: introduce shell file to run link-check-plugin
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
<<<<<<< refs/remotes/origin/master
if [[ $RESULT != 0 ]]; then false; fi

=======
if [[ $RESULT != 0 ]]; then false; fi
>>>>>>> config: introduce shell file to run link-check-plugin


<<<<<<< refs/remotes/origin/master
#This script is used at https://app.codeship.com/projects/124310
#Run "firefox target/site/linkcheck.html" after completion to review html report

=======
>>>>>>> config: introduce shell file to run link-check-plugin
set -e

uname -a
mvn --version
curl -I https://sourceforge.net/projects/checkstyle/
mvn clean site -Dcheckstyle.ant.skip=true -DskipTests -DskipITs -Dpmd.skip=true -Dfindbugs.skip=true -Dcobertura.skip=true -Dcheckstyle.skip=true
echo "------------ grep of linkcheck.html--BEGIN"
<<<<<<< refs/remotes/origin/master
# "grep ... | cat" is required command is running in "set -e" mode and grep could return exit code 1 if nothing is matching
=======
>>>>>>> config: introduce shell file to run link-check-plugin
grep externalLink target/site/linkcheck.html | cat
echo "------------ grep of linkcheck.html--END"
RESULT=$(grep externalLink target/site/linkcheck.html | grep -v 'Read timed out' | wc -l)
echo 'Exit code:'$RESULT
<<<<<<< refs/remotes/origin/master
if [[ $RESULT != 0 ]]; then false; fi

=======
if [[ $RESULT != 0 ]]; then false; fi
>>>>>>> config: introduce shell file to run link-check-plugin
