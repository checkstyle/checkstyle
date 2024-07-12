#!/bin/bash

set -e

JAR_PATH="$1"

INPUT_PATHS=(
chapter4formatting/rule4842fallthrough/InputFallThrough.java
chapter6programpractice/rule62donotignoreexceptions/InputEmptyFinallyBlock.java
chapter6programpractice/rule64finalizers/InputNoFinalizeExtend.java
chapter6programpractice/rule64finalizers/InputNoFinalizer.java
chapter6programpractice/rule62donotignoreexceptions/InputEmptyFinallyBlock.java
)

for INPUT_PATH in "${INPUT_PATHS[@]}"; do
  java -jar "$JAR_PATH" --replace src/it/resources/com/google/checkstyle/test/"$INPUT_PATH"
done
