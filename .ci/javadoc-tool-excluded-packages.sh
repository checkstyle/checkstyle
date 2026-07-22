#!/bin/bash

# Format: <source-root> <package>
#
# Each entry is a temporary package-level exclusion from the JDK javadoc syntax validation.
source_root=src/test/resources
checks_package=com.puppycrawl.tools.checkstyle.checks
javadoc_package=$checks_package.javadoc

JAVADOC_TOOL_EXCLUDED_PACKAGES=(
)
