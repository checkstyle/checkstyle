#!/bin/bash
# Check if Maven cache is good enough to run offline
# Fixes issue #18200 where Maven tries to download artifacts that are already cached

MAVEN_REPO="${MAVEN_REPO_LOCAL:-${MAVEN_CACHE_FOLDER:-$HOME/.m2/repository}}"

check_cache_completeness() {
  if [ ! -d "$MAVEN_REPO" ]; then
    echo "Cache directory missing: $MAVEN_REPO" >&2
    return 1
  fi

  cache_size=$(du -sm "$MAVEN_REPO" 2>/dev/null | cut -f1 2>/dev/null || echo "0")
  
  if [ -z "$cache_size" ] || [ "$cache_size" = "0" ]; then
    echo "Cache size check failed" >&2
    return 1
  fi
  
  # Ensure cache_size is a number before comparison
  case "$cache_size" in
    ''|*[!0-9]*)
      echo "Cache size is not a valid number: $cache_size" >&2
      return 1
      ;;
  esac
  
  # Very lenient threshold - 10MB indicates cache was restored
  # Azure Pipelines cache should have much more than this
  if [ "$cache_size" -lt 10 ]; then
    echo "Cache too small ($cache_size MB), probably incomplete" >&2
    return 1
  fi

  # Check if we have any Maven artifacts at all
  # Just verify the cache isn't completely empty
  if [ -z "$(ls -A "$MAVEN_REPO" 2>/dev/null)" ]; then
    echo "Cache directory is empty" >&2
    return 1
  fi

  echo "Cache looks good ($cache_size MB)" >&2
  return 0
}

should_use_offline() {
  # If explicitly set, use that
  if [ "${MAVEN_OFFLINE}" = "false" ]; then
    return 1
  fi

  if [ "${MAVEN_OFFLINE}" = "true" ]; then
    return 0
  fi

  # Auto-detect: check if cache is good
  if check_cache_completeness; then
    # In PRs, allow network if pom.xml changed (might need new deps)
    if [ "${BUILD_REASON}" = "PullRequest" ]; then
      # Check if we can determine pom.xml changes
      if git rev-parse --verify origin/master >/dev/null 2>&1; then
        if git diff --name-only origin/master...HEAD 2>/dev/null | \
           grep -q "pom.xml" 2>/dev/null; then
          return 1
        fi
      else
        # Can't check, be conservative and allow network
        return 1
      fi
    fi
    return 0
  else
    return 1
  fi
}

case "${1}" in
  check)
    check_cache_completeness
    ;;
  should-offline)
    should_use_offline
    ;;
  *)
    echo "Usage: $0 {check|should-offline}"
    exit 1
    ;;
esac
