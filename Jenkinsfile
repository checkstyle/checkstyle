// Node label that specifies on which slave(s) the job should run
BUILD_SLAVES_LABEL = 'build-servers'

DEFAULT_MAVEN_OPTS = "-Xss256k -Xmx300m -XX:MaxMetaspaceSize=80m -XX:MaxMetaspaceExpansion=10m " +
                     "-Dmaven.test.failure.ignore=false -XshowSettings:vm " + 
                     "-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

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

// See more about Hyper container size at https://hyper.sh/pricing.html
def runOnHyper(HyperSize size, String mavenOpts, String command) {
  
  String name = UUID.randomUUID()

  dir("${WORKSPACE}/") {
    sh "hyper run --name '${name}' --size=${size.toString()} --cidfile ${name}.cid " +
      "--noauto-volume --restart=no --rm -i -e MAVEN_OPTS='${mavenOpts}' " +
      "-v \$(pwd):/usr/local/checkstyle/ " +
      "checkstyle/maven-builder-image:jdk-8u162b12-maven-3.5.3-groovy-2.4.15 " +
      "bash -c '${command}'"
  }
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

    stage ("Prepare (triggered by hand)") {
      when { expression { getCauseDescription(currentBuild).contains("Started by user") } }
        steps {
        echo GREEN("Triggered by hand, so building for master branch")
        deleteDir() /* clean up workspace */
        git 'git@github.com:checkstyle/checkstyle.git' /* clone the master branch */
      }
    }

    stage ("Prepare (triggered by PR)") {
      when { not { expression { getCauseDescription(currentBuild).contains("Started by user") } } }
      steps {
        echo GREEN("Triggered by PR, so building for ${sha1}")
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
      sh "ls | grep '.cid' | xargs -I {} bash -c 'cat {}; echo' | xargs -I {} hyper rm -f {} | :"

      // Clean up workspace
      deleteDir()
    }
  }
}
