// Node label that specifies on which slave(s) the job should run
BUILD_SLAVES_LABEL = 'build-servers'

// 'sha1' envvar is injected by Jenkins GHPRB plugin in case if build is started by pull request
IS_TRIGGERED_BY_PR = env.sha1?.trim()

GIT_BRANCH = '<unknown>'

// Text colours definition
def GREEN( String msg ) { return "\u001B[32m${msg}\u001B[0m" }
def YELLOW( String msg ) { return "\u001B[33m${msg}\u001B[0m" }
def RED( String msg ) { return "\u001B[31m${msg}\u001B[0m" }

def getCause(def build) {
  while(build.previousBuild) {
    build = build.previousBuild
  }

  return build.rawBuild.getCause(hudson.model.Cause$UserIdCause)
}

def getCauseDescription(def build) {
  return getCause(build).shortDescription
}

pipeline {

  agent {
    label "${BUILD_SLAVES_LABEL}"
  }

  options {
    ansiColor('xterm')
  }

  stages {

    stage ("Initial") {
      steps {
        echo GREEN("${getCauseDescription(currentBuild)}")

        script {
          // If build is triggered by PR, use PR branch, otherwise use master
          if (IS_TRIGGERED_BY_PR) {
            GIT_BRANCH = env.sha1
          } else {
            GIT_BRANCH = 'master'
          }
        }

        echo GREEN("Branch: $GIT_BRANCH")

        // Debug: print all the build envvars
        // echo sh(returnStdout: true, script: 'env')

        sh "mvn --version"
      }
    }

    stage ("Prepare (triggered by PR)") {
      when { expression { IS_TRIGGERED_BY_PR } }
      steps {
        echo "${GREEN('PR:')} ${ghprbPullAuthorLoginMention} ${ghprbPullLink} $ghprbPullTitle"
      }
    }

    stage ("Prepare (triggered by hand)") {
      when { not { expression { IS_TRIGGERED_BY_PR } } }
        steps {
        echo GREEN("Triggered by hand, so building for master branch")
        deleteDir() /* clean up workspace */
        git 'git@github.com:checkstyle/checkstyle.git' /* clone the master branch */
      }
    }

    stage('Build') {
      parallel {
        stage('Package') {
          steps {
            sh "mvn -B package"
          }
        }
      }
    }
  }

  post {
    always {
      // Clean up workspace
      deleteDir()
    }
  }
}
