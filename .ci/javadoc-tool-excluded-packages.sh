#!/bin/bash

# Format: <source-root> <package>
#
# Each entry is a temporary package-level exclusion from the JDK javadoc syntax validation.
source_root=src/test/resources
checks_package=com.puppycrawl.tools.checkstyle.checks
javadoc_package=$checks_package.javadoc

JAVADOC_TOOL_EXCLUDED_PACKAGES=(
  "$source_root $checks_package.annotation.missingdeprecated"
  "$source_root $checks_package.annotation.missingoverride"
  "$source_root $javadoc_package.abstractjavadoc"
  "$source_root $javadoc_package.javadocmethod"
  "$source_root $javadoc_package.javadocpackage.annotation"
  "$source_root $javadoc_package.javadocstyle"
  "$source_root $javadoc_package.javadocstyle.pkginfo.annotation"
  "$source_root $javadoc_package.javadocstyle.pkginfo.valid"
  "$source_root $javadoc_package.javadoctype"
  "$source_root $javadoc_package.missingjavadoctype"
  "$source_root $javadoc_package.nonemptyatclausedescription"
  "$source_root com.puppycrawl.tools.checkstyle.javadocpropertiesgenerator"
)
