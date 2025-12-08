#!/bin/bash
set -e

DEFAULT_JAVA_RELEASE=21
if [ -z "$1" ]; then
    echo "No parameters supplied!"
    echo "Usage %0 <CLASSPATH> [RELEASE]"
    echo "    CLASSPATH:  The classpath of the project and it's libraries to compile (required)."
    echo "    RELEASE:    The optional Java release. Default is ${DEFAULT_JAVA_RELEASE}."
    exit 1
fi
JAVA_RELEASE=${2:-$DEFAULT_JAVA_RELEASE}

ECLIPSE_URL="http://ftp-stud.fht-esslingen.de/pub/Mirrors/eclipse/eclipse/downloads/drops4"

#hard coded versions until #18251
ECJ_MAVEN_VERSION="R-4.37-202509050730" 

echo "Using pinned eclipse release: $ECJ_MAVEN_VERSION"

ECJ_JAR=$(wget --quiet -O- "$ECLIPSE_URL/$ECJ_MAVEN_VERSION/" | grep -o "ecj-[^\"]*" | head -n1)
ECJ_PATH=~/.m2/repository/$ECJ_MAVEN_VERSION/$ECJ_JAR

if [ ! -f "$ECJ_PATH" ]; then
    echo "$ECJ_PATH is not found, downloading ..."
    cd target
    wget $ECLIPSE_URL/"$ECJ_MAVEN_VERSION"/"$ECJ_JAR"
    echo "test jar after download:"
    jar -tvf "$ECJ_JAR" > /dev/null
    echo "check compiler options"
    ECJ_OPTIONS=org/eclipse/jdt/internal/compiler/impl/CompilerOptions.class
    ECJ_OPTIONS_PATTERN='org.eclipse.jdt.core.compiler.problem.[^"]*'
    jar -xf "$ECJ_JAR" $ECJ_OPTIONS
    javap -constants $ECJ_OPTIONS | grep -o "$ECJ_OPTIONS_PATTERN" | sort > ecj.opt
    grep -o "^[^=#]*" ../config/org.eclipse.jdt.core.prefs | sort > cs.opt
    OPTIONS_DIFF=$(diff --unified cs.opt ecj.opt | cat)
    if [ "${OPTIONS_DIFF}" != "" ] ; then
        echo "JDT compiler options diff:"
        echo "${OPTIONS_DIFF}"
        echo please update "config/org.eclipse.jdt.core.prefs" file
        exit 1
    fi
    mkdir -p "$(dirname "$ECJ_PATH")"
    cp "$ECJ_JAR" "$ECJ_PATH"
    cd ..
fi

mkdir -p target/classes target/test-classes target/eclipse

RESULT_FILE=target/eclipse/report.txt

echo "Executing eclipse compiler, output is redirected to $RESULT_FILE..."
echo "java -jar $ECJ_PATH -target ${JAVA_RELEASE} -source ${JAVA_RELEASE} -cp $1  ..."

set +e
java -jar "$ECJ_PATH" -target "${JAVA_RELEASE}" -source "${JAVA_RELEASE}" -encoding UTF-8 -cp "$1" \
        -d target/eclipse-compile \
        -properties config/org.eclipse.jdt.core.prefs \
        -enableJavadoc \
        -nowarn:[target/generated-sources/antlr] \
        -nowarn:[src/test/resources/] \
        -nowarn:[src/test/resources-noncompilable/] \
        -nowarn:[src/it/resources/] \
        -nowarn:[src/it/resources-noncompilable/] \
        src/main/java \
        target/generated-sources/antlr \
        src/test/java \
        src/it/java \
    > $RESULT_FILE 2>&1
EXIT_CODE=$?
set -e

if [[ $EXIT_CODE != 0 ]]; then
  echo "Content of $RESULT_FILE:"
  cat $RESULT_FILE
  false
else
    JAVA_RELEASE=24
    echo "In eclipse, Preview of features is supported only at the latest source level"
    echo "JAVA_RELEASE isset to ${JAVA_RELEASE}"
    echo "Executing eclipse compiler on generated resources with all warnings suppressed..."
    set +e
    # Compile test resources with all warnings suppressed
    java -jar "$ECJ_PATH" -target "${JAVA_RELEASE}" -source "${JAVA_RELEASE}" -cp "$1" \
            -d target/eclipse-compile \
            --enable-preview \
            -nowarn \
            src/main/java \
            src/test/java \
            src/it/java \
            target/generated-sources/antlr \
            src/test/resources \
            src/it/resources \
        > $RESULT_FILE 2>&1
    EXIT_CODE=$?
    set -e

    if [[ $EXIT_CODE != 0 ]]; then
      echo "Content of $RESULT_FILE:"
      cat $RESULT_FILE
      false
    fi
fi

