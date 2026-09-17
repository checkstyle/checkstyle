#!/bin/bash

set -e

# Get the config file from arguments
if [ "$#" -ne 1 ]; then
  echo "Missing arguments!"
  echo "Usage: $0 CONFIG_FILE"
  exit 1
fi

CONFIG_FILE=$1

# max difference tolerance in %
THRESHOLD_PERCENTAGE=10
# JDK version
JDK_VERSION=25
# sample project path: only a subset of the sample project is benchmarked so that
# building and running master on the same runner (for a dynamic baseline) stays cheap
SAMPLE_PROJECT="./.ci-temp/jdk$JDK_VERSION/src/java.base"
# suppression file
SUPPRESSION_FILE="./config/projects-to-test/openjdk$JDK_VERSION-excluded.files"
# number of timed executions per jar
NUM_EXECUTIONS=3
# working tree used to build master without disturbing the patch checkout
MASTER_WORKTREE="../checkstyle-perf-master"

# execute a command and time it
# $TEST_COMMAND: command being timed
time_command() {
  # execute the command and time it
  /usr/bin/time -o time.temp -q -f "%e" "$@" &>result.tmp

  cat time.temp
}

# package the checkstyle all-in-one jar in the current working tree
# echoes the absolute path of the produced jar
package_jar() {
  ./mvnw -e --no-transfer-progress -Passembly,no-validations package 1>&2
  find "$PWD/target/" -type f -name "checkstyle-*-all.jar"
}

# execute the benchmark a few times to calculate the average execution time
# $JAR_PATH: path of the jar file being benchmarked
execute_benchmark() {
  local JAR_PATH=$1
  if [ -z "$JAR_PATH" ]; then
      echo "Missing JAR_PATH as an argument."
      exit 1
  fi

  local TOTAL_SECONDS=0

  for ((i = 0; i < NUM_EXECUTIONS; i++)); do
    local CMD=(java -jar "$JAR_PATH" -c "$CONFIG_FILE" \
      -x .git -x module-info.java "$SAMPLE_PROJECT")
    local BENCHMARK=($(time_command "${CMD[@]}"))
    echo "  Run $((i + 1))/$NUM_EXECUTIONS ($JAR_PATH): ${BENCHMARK}s" 1>&2
    TOTAL_SECONDS=$(echo "$TOTAL_SECONDS + $BENCHMARK" | bc)
  done

  # average execution time
  echo "scale=2; $TOTAL_SECONDS / $NUM_EXECUTIONS" | bc
}

# compare baseline and patch benchmarks
# $BASELINE_SECONDS execution time of master
# $EXECUTION_TIME_SECONDS execution time of the patch
compare_results() {
  local BASELINE_SECONDS=$1
  local EXECUTION_TIME_SECONDS=$2
  if [ -z "$BASELINE_SECONDS" ] || [ -z "$EXECUTION_TIME_SECONDS" ]; then
        echo "Missing BASELINE_SECONDS or EXECUTION_TIME_SECONDS as an argument."
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

[ ! -d "$SAMPLE_PROJECT" ] &&
  { echo "Directory $SAMPLE_PROJECT DOES NOT exist." ; exit 1 ; }

# add suppressions to config file once; both jars are benchmarked with the same config
sed -i "/  <!-- Filters -->/r $SUPPRESSION_FILE" \
      "$CONFIG_FILE"

# package patch (current checkout)
echo "Packaging patch..."
cp "$(package_jar)" patch-all.jar

# package master in a separate worktree so the patch config and sample stay untouched
echo "Packaging master..."
git fetch --no-tags --depth=1 origin master
git worktree add --detach "$MASTER_WORKTREE" FETCH_HEAD
cp "$(cd "$MASTER_WORKTREE" && package_jar)" base-all.jar
git worktree remove --force "$MASTER_WORKTREE"

# start benchmark, measuring both on the same runner
echo "Benchmark launching..."
echo "Benchmarking master (baseline)..."
BASELINE_SECONDS="$(execute_benchmark base-all.jar)"
echo "Benchmarking patch..."
AVERAGE_IN_SECONDS="$(execute_benchmark patch-all.jar)"

# print the command execution result
echo "================ MOST RECENT COMMAND RESULT ================="
cat result.tmp

echo "===================== BENCHMARK SUMMARY ===================="
echo "Execution Time Baseline: ${BASELINE_SECONDS} s"
echo "Average Execution Time: ${AVERAGE_IN_SECONDS} s"
echo "============================================================"

# compare result with dynamic master baseline
compare_results "$BASELINE_SECONDS" "$AVERAGE_IN_SECONDS"

exit $?
