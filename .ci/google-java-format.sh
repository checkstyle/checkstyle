#!/bin/bash

set -e

JAR_PATH="$1"

INPUT_PATHS=($(find src/it/resources/com/google/checkstyle/test/ -name "Input*.java" \
    | sed "s|src/it/resources/com/google/checkstyle/test/||" \
    | grep -v "rule711generalform" \
    | grep -v "rule712paragraphs/InputIncorrectRequireEmptyLineBeforeBlockTagGroup.java" \
    | grep -v "rule712paragraphs/InputIncorrectJavadocParagraph.java" \
    | grep -v "rule713atclauses" \
    | grep -v "rule734nonrequiredjavadoc" \
    | grep -v "rule53camelcase" \
    | grep -v "rule522classnames" \
    | grep -v "rule41" \
    | grep -v "rule42" \
    | grep -v "rule43onestatement" \
    | grep -v "rule44columnlimit" \
    | grep -v "rule45" \
    | grep -v "rule461verticalwhitespace" \
    | grep -v "rule462" \
    | grep -v "rule48" \
    | grep -v "rule3sourcefile" \
    | grep -v "rule331nowildcard" \
    | grep -v "rule332nolinewrap" \
    | grep -v "rule333orderingandspacing" \
    | grep -v "rule3421overloadsplit" \
    | grep -v "rule231filetab" \
    ))

for INPUT_PATH in "${INPUT_PATHS[@]}"; do
  java -jar "$JAR_PATH" --replace src/it/resources/com/google/checkstyle/test/"$INPUT_PATH"
done
