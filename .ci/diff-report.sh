#!/bin/bash
set -e

source ./.ci/util.sh

case $1 in

# Downloads all files necessary to generate regression report.
download-files)
  checkForVariable "GITHUB_TOKEN"
  echo "Downloading files..."

  # check for projects link from PR, if not found use default from contribution repo
  LINK="${LINK_FROM_PR:-$DEFAULT_PROJECTS_LINK}"

  # get projects file
  curl --fail-with-body -X GET "${LINK}" \
    -H "Accept: application/vnd.github+json" \
    -H "Authorization: token $GITHUB_TOKEN" \
    -o project.properties

  if [ -n "$NEW_MODULE_CONFIG_LINK" ]; then
    curl --fail-with-body -X GET "${NEW_MODULE_CONFIG_LINK}" \
      -H "Accept: application/vnd.github+json" \
      -H "Authorization: token $GITHUB_TOKEN" \
      -o new_module_config.xml
  fi

  if [ -n "$DIFF_CONFIG_LINK" ]; then
    curl --fail-with-body -X GET "${DIFF_CONFIG_LINK}" \
      -H "Accept: application/vnd.github+json" \
      -H "Authorization: token $GITHUB_TOKEN" \
      -o diff_config.xml
  fi

  if [ -n "$PATCH_CONFIG_LINK" ]; then
    curl --fail-with-body -X GET "${PATCH_CONFIG_LINK}" \
      -H "Accept: application/vnd.github+json" \
      -H "Authorization: token $GITHUB_TOKEN" \
      -o patch_config.xml
  fi
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac
