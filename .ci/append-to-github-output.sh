#!/bin/bash

set -e

NAME=$1
VALUE=$2

if [[ "$#" != "2" ]]; then
  echo "not all parameters are set"
  echo "Usage: $BASH_SCRIPT <variable name> <variable value>"
  exit 1
fi

# Select random value for EOF as a delimiter.
EOF=$(dd if=/dev/urandom bs=15 count=1 status=none | base64)
{
  echo "$NAME<<$EOF"
  echo "$VALUE"
  echo "$EOF"
} >> "$GITHUB_OUTPUT"
