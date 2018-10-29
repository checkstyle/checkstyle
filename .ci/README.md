ATTENTION:
  All scripts in this folder expect to be launched from root folder of repository

Example of usage:
  export GOAL="all-sevntu-checks" && ./.ci/travis/travis.sh

  export TRAVIS_PULL_REQUEST="" && export GOAL="releasenotes-gen" && ./.ci/travis/travis.sh 
