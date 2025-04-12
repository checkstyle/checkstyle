#!/bin/bash
# Attention, this script is used with GitHub Actions
set -e

CODENARC_REPORT=$(groovy ./.ci/codenarc.groovy ./.ci/ *.groovy)
CLEAN_CODENARC_REPORT="(p1=0; p2=0; p3=0)"
echo "$CODENARC_REPORT"
if [[ "$CODENARC_REPORT" != *"$CLEAN_CODENARC_REPORT"* ]]; then
  exit 1
fi
