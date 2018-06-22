JENKINS_NODE = env.JENKINS_NODE ?: 'master'

// Text colours definition
def GREEN( String msg ) { return "\u001B[32m${msg}\u001B[0m" }
def YELLOW( String msg ) { return "\u001B[33m${msg}\u001B[0m" }
def RED( String msg ) { return "\u001B[31m${msg}\u001B[0m" }

def LOGS_PUSH( String str ) {
  LOGS.push( str )
}

def LOGS_DUMP() {
  echo LOGS.join("\n")
}

LOGS = []
JENKINS_BUILD_USER = "Unknown"

// The build pipeline
pipeline {

  // Specify the Jenkins to to run on
  agent { node { label "${JENKINS_NODE}" } }

  // Various build and build plugin's options
  options {
    ansiColor('xterm')
  }

  stages {

    stage("Build") {
      agent { node { label "${JENKINS_NODE}" } }
      steps {
        echo GREEN("STARTED: Stage 'Noop'")
        dir("${WORKSPACE}/"){
          
          // Running the ls command as a 'no-op' is helpful to make sure that the proper sources were checked out
          sh "ls -lah"
          
          LOGS_PUSH( GREEN("Noop 'ls -lah' task finished") )

        }
        echo GREEN("FINISHED: Stage 'Build'")
      }
    }

  }

  post {
    // Always print build logs
    always {
      LOGS_DUMP()
    }
  }

}
