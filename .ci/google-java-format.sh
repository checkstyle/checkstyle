#!/bin/bash

set -e

JAR_PATH="$1"

EXCLUDED_LIST="config/google-java-format/excluded/compilable-input-paths.txt \
config/google-java-format/excluded/noncompilable-input-paths.txt"

SUPPRESSION_LIST="config/google-java-format/suppressions/suppress-diff-lt-100.txt \
config/google-java-format/suppressions/suppress-diff-lt-500.txt \
config/google-java-format/suppressions/suppress-diff-gt-500.txt"

is_suppressed() {
  local name="$1"
  for f in $SUPPRESSION_LIST; do
    if grep -Fxq "$name" "$f"; then
      return 0
    fi
  done
  return 1
}

echo "Checking that all excluded java files in this script have matching InputFormatted* file:"
NOT_FOUND_CONTENT=$(cat $EXCLUDED_LIST \
  | sed -E 's/.*Input([^\.]+)\..*java.*/\1/' \
  | while read -r name; do \
    [[ $(find ./src -type f -name "InputFormatted${name}.java") ]] \
    || echo "Create InputFormatted${name}.java for Input${name}.java";\
  done)

if [[ $(echo -n "$NOT_FOUND_CONTENT" | wc --chars) -eq 0 ]]; then
  echo "Excluded Input files matches to InputFormatted files."
else
  echo "not found matches: $NOT_FOUND_CONTENT"
  exit 1
fi

echo "Checking that all excluded java files have same size as matching InputFormatted* file:"
MISMATCH_CONTENT=$(cat $EXCLUDED_LIST \
  | while read -r path; do
    size=$(sed -E 's|^[[:space:]]+||; s|//.*||' "$path" | wc -c)
    name=$(echo "$path" | sed -E 's/.*Input([^\.]+)\.java.*/\1/')
    formatted_path=$(find ./src -type f -name "InputFormatted${name}.java")
    size_formatted=$(sed -E 's|^[[:space:]]+||; s|//.*||' "$formatted_path" | wc -c)
    diff=$(( size - size_formatted ))
    if (( diff < 0 )); then
      diff=$(( -diff ))
    fi
    if is_suppressed "$name";then
      continue
    fi
    if (( diff > 50 )); then
      echo "Difference in $name and InputFormatted${name} is more than 50 bytes ${diff}"
    fi
  done)

if [[ $(echo -n "$MISMATCH_CONTENT" | wc --chars) -eq 0 ]]; then
  echo "All excluded Input files match their InputFormatted counterparts in size."
else
  echo "Size mismatches found:"
  echo "$MISMATCH_CONTENT"
  exit 1
fi

echo "Formatting all Input files file at src/it/resources/com/google/checkstyle/test :"
COMPILABLE_INPUT_PATHS=($(find src/it/resources/com/google/checkstyle/test/ -name "Input*.java" \
  | grep -v -x -f config/google-java-format/excluded/compilable-input-paths.txt
  ))

for INPUT_PATH in "${COMPILABLE_INPUT_PATHS[@]}"; do
  java -jar "$JAR_PATH" --replace "$INPUT_PATH"
done

echo "Formatting all Non-compilable Input files file at src/it/resources-noncompilable/com/google/checkstyle/test :"
NON_COMPILABLE_INPUT_PATHS=($(find src/it/resources-noncompilable/com/google/checkstyle/test/ -name "Input*.java" \
  | grep -v -x -f config/google-java-format/excluded/noncompilable-input-paths.txt
  ))

for INPUT_PATH in "${NON_COMPILABLE_INPUT_PATHS[@]}"; do
  java -jar "$JAR_PATH" --replace "$INPUT_PATH"
done
