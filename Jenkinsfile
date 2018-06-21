
GIT_CHECKSTYLE_URL = "ssh://git@github.com/Nurego/metering.git"
JENKINS_NODE = env.JENKINS_NODE ?: 'master'

def GREEN( String msg ) {
  return "\u001B[32m${msg}\u001B[0m"
}

def YELLOW( String msg ) {
  return "\u001B[33m${msg}\u001B[0m"
}

def RED( String msg ) {
  return "\u001B[31m${msg}\u001B[0m"
}

def GIT_CHECKOUT( String url, String branch ) {
  try {
    git url: "${url}", branch: "${branch}", credentialsId: "${GIT_CREDENTIAL_ID}"
  } catch(e) {
    checkout scm: [
      $class: 'GitSCM',
      userRemoteConfig: [[
        url: "${url}",
        credentialsId: "${GIT_CREDENTIAL_ID}"
      ]],
      branches: [[
        name: "${branch}"
      ]],
      poll: false
    ]
  }
}

def LOGS_PUSH( String str ) {
  LOGS.push( str )
}

def LOGS_DUMP() {
  echo LOGS.join("\n")
}

LOGS = []
JENKINS_BUILD_USER = "Unknown"

pipeline {
  agent { node { label "${JENKINS_NODE}" } }
  options {
    ansiColor('xterm')
  }

  stages {

    stage("Build") {
      agent { node { label "${JENKINS_NODE}" } }
      steps {
        echo GREEN("STARTED: Stage 'Build'")
        dir("${WORKSPACE}/"){
          
          sh "ls -lah"
          LOGS_PUSH( GREEN("Noop task finished") )

        }
        echo GREEN("FINISHED: Stage 'Build'")
      }
    }

  }

  post {
    always {
      LOGS_DUMP()
    }
  }

}
