#!/usr/bin/env bash

set -e

PREV_YEAR=$1
CURR_YEAR=$2
DIR=$3

OLD_VALUE="// Copyright (C) 2001-$PREV_YEAR the original author or authors."
NEW_VALUE="// Copyright (C) 2001-$CURR_YEAR the original author or authors."

find "$DIR" -type f \( -name *.java -o -name *.header -o -name *.g4 \) \
  -exec sed -i "s|$OLD_VALUE|$NEW_VALUE|g" {} +

BASEDIR=$(pwd)
echo "Distinct Diff in $DIR is:"
cd "$DIR"
git diff | grep -Eh "^\+"  | grep -v "+++ b" | sort | uniq
cd "$BASEDIR"
