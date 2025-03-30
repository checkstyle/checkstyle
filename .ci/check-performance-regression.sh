#!/bin/bash

set -e

# Get the baseline seconds and config file from arguments
if [ "$#" -ne 2 ]; then
  echo "Missing arguments!"
  echo "Usage: $0 BASELINE_SECONDS CONFIG_FILE"
  exit 1
fi

BASELINE_SECONDS=$1
CONFIG_FILE=$2

# max difference tolerance in %
THRESHOLD_PERCENTAGE=300
# JDK version
JDK_VERSION=21
# sample project path
SAMPLE_PROJECT="./.ci-temp/checkstyle"


# execute a command and time it
# $TEST_COMMAND: command being timed
time_command() {
  # execute the command and time it
  /usr/bin/time -o time.temp -q -f "%e" "$@" &>result.tmp

  cat time.temp
}

# execute the benchmark a few times to calculate the average metrics
# $JAR_PATH: path of the jar file being benchmarked
execute_benchmark() {
  local JAR_PATH=$1
  if [ -z "$JAR_PATH" ]; then
      echo "Missing JAR_PATH as an argument."
      exit 1
  fi

  local TOTAL_SECONDS=0
  local NUM_EXECUTIONS=3

  [ ! -d "$SAMPLE_PROJECT" ] &&
    echo "Directory $SAMPLE_PROJECT DOES NOT exist." | exit 1


  for ((i = 0; i < NUM_EXECUTIONS; i++)); do
    local CMD=(java -jar "$JAR_PATH" -c "$CONFIG_FILE" \
      -x .git -x module-info.java "$SAMPLE_PROJECT")
    local BENCHMARK=($(time_command "${CMD[@]}"))
    TOTAL_SECONDS=$(echo "$TOTAL_SECONDS + $BENCHMARK" | bc)
  done

  # average execution time in patch
  local AVERAGE_IN_SECONDS=$(echo "scale=2; $TOTAL_SECONDS / $NUM_EXECUTIONS" | bc)
  echo "$AVERAGE_IN_SECONDS"
}

# compare baseline and patch benchmarks
# $EXECUTION_TIME_SECONDS execution time of the patch
compare_results() {
  local EXECUTION_TIME_SECONDS=$1
  if [ -z "$EXECUTION_TIME_SECONDS" ]; then
        echo "Missing EXECUTION_TIME_SECONDS as an argument."
        return 1
    fi
  # Calculate absolute percentage difference for execution time
  local DEVIATION_IN_SECONDS=$(echo "scale=4; \
    ((${EXECUTION_TIME_SECONDS} - ${BASELINE_SECONDS}) / ${BASELINE_SECONDS}) * 100" | bc)
  DEVIATION_IN_SECONDS=$(echo "scale=4; \
    if ($DEVIATION_IN_SECONDS < 0) -($DEVIATION_IN_SECONDS) else $DEVIATION_IN_SECONDS" | bc)

  echo "Execution Time Difference: $DEVIATION_IN_SECONDS%"

  # Check if differences exceed the maximum allowed difference
  if (( $(echo "$DEVIATION_IN_SECONDS > $THRESHOLD_PERCENTAGE" | bc -l) )); then
    echo "Difference exceeds the maximum allowed difference (${DEVIATION_IN_SECONDS}% \
     > ${THRESHOLD_PERCENTAGE}%)!"
    return 1
  else
    echo "Difference is within the maximum allowed difference (${DEVIATION_IN_SECONDS}% \
     <= ${THRESHOLD_PERCENTAGE}%)."
    return 0
  fi
}

# package patch
mvn -e --no-transfer-progress -Passembly,no-validations package

# start benchmark
echo "Benchmark launching..."
AVERAGE_IN_SECONDS="$(execute_benchmark "$(find "./target/" -type f -name "checkstyle-*-all.jar")")"

# print the command execution result
echo "================ MOST RECENT COMMAND RESULT ================="
cat result.tmp

echo "===================== BENCHMARK SUMMARY ===================="
echo "Execution Time Baseline: ${BASELINE_SECONDS} s"
echo "Average Execution Time: ${AVERAGE_IN_SECONDS} s"
echo "============================================================"

# compare result with baseline
compare_results "$AVERAGE_IN_SECONDS"

exit $?
