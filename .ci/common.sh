#!/bin/bash
# Attention, there is no "-x" to avoid issues during execution
set -e

DEBUG=true

# result of command goes to $RUN_JOB
# 0 = not run (skip)
# 1 = run (no skip)
function should_run_job {
    local SKIP_JOB_BY_FILES=$1
    local SKIP_FILES=$2
    # OUT is needed to display the result of the commands for debugging
    # because some CIs don't like them being executed directly
    local OUT=""

    echo "Initial value of SKIP_JOB_BY_FILES: $SKIP_JOB_BY_FILES"
    echo "Files that are allowed to be skipped: $SKIP_FILES"

    if [[ $SKIP_JOB_BY_FILES != 'false' ]]; then
         if [[ $DEBUG == "true" ]]; then
              OUT=$(git branch -r)
              echo "List of branches: $OUT"
              OUT=$(git log -10 --format="%h %B")
              echo "Current Branch log: $OUT"
              OUT=$(git log origin/master -10 --format="%h %B")
              echo "origin/master log: $OUT"
         fi

         # Travis merges the PR commit into origin/master
         # This identifies the PR's original commit
         # if it notices a merge commit
         local HEAD=$(git rev-parse HEAD)

         if git show --summary HEAD | grep ^Merge: ; then
              HEAD=$(git log -n 1 --no-merges --pretty=format:"%H")
         fi

         # Identify previous commit to know how much to examine
         # Script assumes we are only working with 1 commit if we are in master
         # Otherwise, it looks for the common ancestor with master
         local PREVIOUS_COMMIT=$(git rev-parse HEAD~1)

         if [[ $DEBUG == "true" ]]; then
              echo "Head commit: $HEAD"
              OUT=$(git branch -a --contains "$HEAD")
              echo "Master contains head commit: $OUT"
              OUT=$(git branch -a --contains "$HEAD" | grep " origin/master$" || true)
              echo "Master contains head commit (filtered): $OUT"
         fi

         # We are not in master if master does not contain the head commit
         if [[ $(git branch -a --contains "$HEAD" | grep " origin/master$" \
                    | wc -c ) == 0 ]]; then
              PREVIOUS_COMMIT=$(git merge-base origin/master "$HEAD")
         fi

         echo "Previous Commit to start with: $PREVIOUS_COMMIT"

         # Check to ensure head and previous commit are not the same.
         # This is an error if it happens.
         if [[ "$HEAD" == "$PREVIOUS_COMMIT" ]]; then
              echo "Error: previous and head commit are the same and this shouldn't happen."
              sleep 5s
              exit 1
         fi

         if [[ $DEBUG == "true" ]]; then
              OUT=$(git diff --name-only "$HEAD" "$PREVIOUS_COMMIT")
              echo "Files between top commit and previous: $OUT"
              OUT=$(git diff --name-only "$HEAD" "$PREVIOUS_COMMIT" | grep -vE "$SKIP_FILES" | cat)
              echo "Files should not skip: $OUT"
         fi

         # Identifies if the files involved in the commits between head and previous
         # is more than the list of skippable files
         if [[ $(git diff --name-only "$HEAD" "$PREVIOUS_COMMIT" \
                    | grep -vE "$SKIP_FILES" | cat | wc -c | sed 's/^ *//' ) -gt 0 ]]; then
              SKIP_JOB_BY_FILES="false"
         else
              SKIP_JOB_BY_FILES="true"
         fi
    else
        echo "CI step logic doesn't want a skip by files"
    fi

    echo "SKIP_JOB_BY_FILES: $SKIP_JOB_BY_FILES"

    local SKIP_JOB_BY_COMMIT="false"

    if [[ $DEBUG == "true" ]]; then
         OUT=$(git log -1 --format=%B)
         echo "Top commit message: $OUT"
    fi

    # Note: this command only works in master branch
    if [ "$(git log -1 --format=%B | grep -E "\[maven-release-plugin\] prepare release" \
              | cat | wc -l)" -lt 1 ];
    then
        SKIP_JOB_BY_COMMIT="false"
    else
        SKIP_JOB_BY_COMMIT="true"
    fi

    echo "SKIP_JOB_BY_COMMIT: $SKIP_JOB_BY_COMMIT"

    if [[ $SKIP_JOB_BY_FILES == 'false' && $SKIP_JOB_BY_COMMIT == 'false' ]]; then
         echo "CI should be run."
         RUN_JOB=1
    else
         echo "CI should not run.";
         RUN_JOB=0
    fi
}
