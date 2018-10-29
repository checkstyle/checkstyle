// Node label that specifies on which slave(s) the job should run
BUILD_SLAVES_LABEL = 'build-servers'

DEFAULT_MAVEN_OPTS = "-Xss256k -Xmx300m -XX:MaxMetaspaceSize=80m -XX:MaxMetaspaceExpansion=10m " +
                     "-Dmaven.test.failure.ignore=false -XshowSettings:vm " + 
                     "-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

// 'sha1' envvar is injected by Jenkins GHPRB plugin in case if build is started by pull request
IS_TRIGGERED_BY_PR = env.sha1?.trim()

GIT_BRANCH = '<unknown>'

enum HyperSize {
  S4("s4"),
  M1("m1"),
  M2("m2");

  private size;

  HyperSize(String size) {
    this.size = size
  }
  
  public String toString() {
    return size;
  }
}

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

// See more about Hyper container sizes at https://hyper.sh/pricing.html
def runOnHyper(HyperSize size, String mavenOpts, String command) {
  
  String name = UUID.randomUUID()

  image = 'checkstyle/maven-builder-image:jdk-8u162b12-maven-3.5.3-groovy-2.4.15'
  containerTTL = 1800 // sec

  echo GREEN("To try this build locally, execute the following command: [ $command ]")

  // 1. Start empty Hyper container with timeout and let it put it's id to the file for further use
  retry(3) {
    sh "hyper -l=warn run -d --name '${name}' --size=${size.toString()} --cidfile ${name}.cid " +
       " --noauto-volume --restart=no -v \$(pwd):/usr/local/checkstyle/ " + 
       " -e MAVEN_OPTS='${mavenOpts}' ${image} sleep '${containerTTL}'"
  }

  // 2. Run the build command inside the container
  sh "hyper -l=info exec -i '${name}' bash -c '${command}'"

  // 3. Cleanup the container as soon as after build finish
  sh "hyper rm -fv '${name}' | cat"
}

def runOnHyperS4(String command) {
  runOnHyper(HyperSize.S4, DEFAULT_MAVEN_OPTS, command)
}

def runOnHyperM1(String command) {
  runOnHyper(HyperSize.M1, DEFAULT_MAVEN_OPTS, command)
}

def runOnHyperM2(String command) {
  runOnHyper(HyperSize.M2, DEFAULT_MAVEN_OPTS, command)
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
        stage('Compile') {
          steps {
            runOnHyperS4("mvn -B compile")
          }
        }
        stage('Package') {
          steps {
            runOnHyperM1("mvn -B package")
          }
        }
      }
    }
  }

  post {
    always {
      // Cleanup any Hyper.sh containers left by the build, if any
      sh "ls | grep '.cid' | xargs -I {} bash -c 'cat {}; echo' | xargs -I {} hyper rm -fv {} | cat"

      // Clean up workspace
      deleteDir()
    }
  }
}
