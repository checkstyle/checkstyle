#!/bin/bash

set -e

JAR_PATH="$1"

NONFORMATTED_LIST=(
  config/google-java-format/excluded/compilable-input-paths.txt
  config/google-java-format/excluded/noncompilable-input-paths.txt
)

SUPPRESS_LTE_100="config/google-java-format/suppressions/suppress-diff-lte-100.txt"
SUPPRESS_GT_100_LTE_500="config/google-java-format/suppressions/suppress-diff-gt-100-lte-500.txt"
SUPPRESS_GT_500="config/google-java-format/suppressions/suppress-diff-gt-500.txt"

MISMATCH_FOUND=0

is_suppressed() {
  local suppress_file="$1"
  local name="$2"
  if grep -Fxq "$name" "$suppress_file"; then
    return 0
  fi
  MISMATCH_FOUND=1
  return 1
}

echo "Checking that all excluded java files in this script have matching InputFormatted* file:"
NOT_FOUND_CONTENT=$(cat "${NONFORMATTED_LIST[@]}" \
  | sed -E 's/.*Input([^\.]+)\.java.*/\1/' \
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
MISMATCH_CONTENT=$(cat "${NONFORMATTED_LIST[@]}" \
  | while read -r path; do
    size=$(sed -E 's|^[[:space:]]+||; s|//.*||' "$path" | wc -c)
    name=$(echo "$path" | sed -E 's/.*Input([^\.]+)\.java.*/\1/')
    formatted_path=$(find ./src -type f -name "InputFormatted${name}.java")
    size_formatted=$(sed -E 's|^[[:space:]]+||; s|//.*||' "$formatted_path" | wc -c)
    diff=$(( size - size_formatted ))
    if (( diff < 0 )); then
      diff=$(( -diff ))
    fi
    if (( diff > 500 )); then
      if is_suppressed "$SUPPRESS_GT_500" "Input${name}.java"; then
        echo -e "\033[1;33mWarning: Difference in Input${name}.java and InputFormatted${name}.java is more than 50 bytes ${diff}\033[0m"
      else
        echo -e "\033[1;31mError: Difference in Input${name}.java and InputFormatted${name}.java is more than 50 bytes ${diff}\033[0m"
      fi
    elif (( diff > 100 )); then
      if is_suppressed "$SUPPRESS_GT_100_LTE_500" "Input${name}.java"; then
        echo -e "\033[1;33mWarning: Difference in Input${name}.java and InputFormatted${name}.java is more than 50 bytes ${diff}\033[0m"
      else
        echo -e "\033[1;31mError: Difference in Input${name}.java and InputFormatted${name}.java is more than 50 bytes ${diff}\033[0m"
      fi
    elif (( diff > 50 )); then
      if is_suppressed "$SUPPRESS_LTE_100" "Input${name}.java"; then
        echo -e "\033[1;33mWarning: Difference in Input${name}.java and InputFormatted${name}.java is more than 50 bytes ${diff}\033[0m"
      else
        echo -e "\033[1;31mError: Difference in Input${name}.java and InputFormatted${name}.java is more than 50 bytes ${diff}\033[0m"
      fi
    fi
  done)

echo "$MISMATCH_CONTENT"
if [ "$MISMATCH_FOUND" -eq 1 ]; then
  echo -e "\033[1;31mOne or more error found.\033[0m"
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
