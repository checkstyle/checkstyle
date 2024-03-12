#!/bin/bash
set -e

source ./.ci/util.sh

# Validates the URL, checks if it is a raw link, and exits if it is not. We
# expect users to provide a raw gist or GitHub link.
validate_url () {
  URL=$1

  VALID_URL_REGEX='^(https?|ftp|file)://[-A-Za-z0-9\+&@#/%?=~_|!:,.;]*[-A-Za-z0-9\+&@#/%=~_|]'
  VALID_URL_REGEX+='\.[-A-Za-z0-9\+&@#/%?=~_|!:,.;]*[-A-Za-z0-9\+&@#/%=~_|]$'

  if [[ -z "$URL" ]]; then
    # It is ok for a URL to be empty, we use this later in the workflow
    # to determine what "mode" we should generate report with.
    echo "URL is empty."
  elif [[ "$URL" == *: ]]; then
    echo "Parameter '${URL}' is incorrectly formatted, please use the following format:"
    echo "'Parameter name: https://gist.githubusercontent.com/username/gist_id/raw/file'"
    echo "Parameter name and URL must be separated by a colon and single space only."
    exit 1
  elif [[ "$URL" != *"raw"* ]]; then
    echo "URL '${URL}' must be a direct raw link to a gist or GitHub file."
    exit 1
  elif ! [[ "$URL" =~ $VALID_URL_REGEX ]]; then
    echo "URL '${URL}' does not match regexp '${VALID_URL_REGEX}'."
    exit 1
  else
    echo "URL '${URL}' is valid."
  fi
}

case $1 in

# Downloads all files necessary to generate regression report to the parent directory.
download-files)
  checkForVariable "GITHUB_TOKEN"
  mkdir .ci-temp
  echo "Downloading files..."

  # check for projects link from PR, if not found use default from contribution repo
  LINK="${LINK_FROM_PR:-$DEFAULT_PROJECTS_LINK}"

  # get projects file
  curl --fail-with-body -X GET "${LINK}" \
    -H "Accept: application/vnd.github+json" \
    -H "Authorization: token $GITHUB_TOKEN" \
    -o .ci-temp/project.properties

  if [ -n "$NEW_MODULE_CONFIG_LINK" ]; then
    curl --fail-with-body -X GET "${NEW_MODULE_CONFIG_LINK}" \
      -H "Accept: application/vnd.github+json" \
      -H "Authorization: token $GITHUB_TOKEN" \
      -o .ci-temp/new_module_config.xml
  fi

  if [ -n "$DIFF_CONFIG_LINK" ]; then
    curl --fail-with-body -X GET "${DIFF_CONFIG_LINK}" \
      -H "Accept: application/vnd.github+json" \
      -H "Authorization: token $GITHUB_TOKEN" \
      -o .ci-temp/diff_config.xml
  fi

  if [ -n "$PATCH_CONFIG_LINK" ]; then
    curl --fail-with-body -X GET "${PATCH_CONFIG_LINK}" \
      -H "Accept: application/vnd.github+json" \
      -H "Authorization: token $GITHUB_TOKEN" \
      -o .ci-temp/patch_config.xml
  fi
  ;;

# Parses the text of the PR description, validates the URLs, and
# sets the environment variables.
# Expects PR description to be in a file called 'text'.
parse-pr-description-text)

  # parse parameters from PR description text
  PROJECTS_FILE_PARAMETER=$(grep "^Diff Regression projects:" .ci-temp/text || true)
  CONFIG_PARAMETER=$(grep "^Diff Regression config:" .ci-temp/text || true)
  NEW_MODULE_CONFIG_PARAMETER=$(grep "^New module config:" .ci-temp/text || true)
  PATCH_CONFIG_PARAMETER=$(grep "^Diff Regression patch config:" .ci-temp/text || true)
  REPORT_LABEL_PARAMETER=$(grep "^Report label:" .ci-temp/text || true)

  echo "Parameters parsed from PR description:"
  echo "PROJECTS_FILE_PARAMETER: '$PROJECTS_FILE_PARAMETER'"
  echo "CONFIG_PARAMETER: '$CONFIG_PARAMETER'"
  echo "NEW_MODULE_CONFIG_PARAMETER: '$NEW_MODULE_CONFIG_PARAMETER'"
  echo "PATCH_CONFIG_PARAMETER: '$PATCH_CONFIG_PARAMETER'"
  echo "REPORT_LABEL_PARAMETER: '$REPORT_LABEL_PARAMETER'"

  # extract URLs from text
  PROJECTS_LINK=$(echo "$PROJECTS_FILE_PARAMETER" | sed -E 's/Diff Regression projects: //')
  CONFIG_LINK=$(echo "$CONFIG_PARAMETER" | sed -E 's/Diff Regression config: //')
  NEW_MODULE_CONFIG_LINK=$(echo "$NEW_MODULE_CONFIG_PARAMETER" | sed -E 's/New module config: //')
  PATCH_CONFIG_LINK=$(echo "$PATCH_CONFIG_PARAMETER" | sed -E 's/Diff Regression patch config: //')
  REPORT_LABEL=$(echo "$REPORT_LABEL_PARAMETER" | sed -E 's/Report label: //')
  # trim
  PROJECTS_LINK=$(echo "$PROJECTS_LINK" | tr -d '[:space:]')
  CONFIG_LINK=$(echo "$CONFIG_LINK" | tr -d '[:space:]')
  NEW_MODULE_CONFIG_LINK=$(echo "$NEW_MODULE_CONFIG_LINK" | tr -d '[:space:]')
  PATCH_CONFIG_LINK=$(echo "$PATCH_CONFIG_LINK" | tr -d '[:space:]')

  echo "URLs extracted from parameters:"
  echo "PROJECTS_LINK: '$PROJECTS_LINK'"
  echo "CONFIG_LINK: '$CONFIG_LINK'"
  echo "NEW_MODULE_CONFIG_LINK: '$NEW_MODULE_CONFIG_LINK'"
  echo "PATCH_CONFIG_LINK: '$PATCH_CONFIG_LINK'"
  echo "REPORT_LABEL: '$REPORT_LABEL'"

  echo "Validating PROJECTS_LINK..."
  validate_url "$PROJECTS_LINK"
  echo "Validating CONFIG_LINK..."
  validate_url "$CONFIG_LINK"
  echo "Validating NEW_MODULE_CONFIG_LINK..."
  validate_url "$NEW_MODULE_CONFIG_LINK"
  echo "Validating PATCH_CONFIG_LINK..."
  validate_url "$PATCH_CONFIG_LINK"

  ./.ci/append-to-github-output.sh "projects_link" "$PROJECTS_LINK"
  ./.ci/append-to-github-output.sh "config_link" "$CONFIG_LINK"
  ./.ci/append-to-github-output.sh "new_module_config_link" "$NEW_MODULE_CONFIG_LINK"
  ./.ci/append-to-github-output.sh "patch_config_link" "$PATCH_CONFIG_LINK"
  ./.ci/append-to-github-output.sh "report_label" "$REPORT_LABEL"
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac
