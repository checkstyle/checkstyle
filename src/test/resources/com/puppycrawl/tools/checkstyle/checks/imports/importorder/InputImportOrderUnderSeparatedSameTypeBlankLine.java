/*
ImportOrder
option = (default)under
groups = /^java\./,javax,org,com,net,edu,io
ordered = (default)true
separated = true
separatedStaticGroups = true
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = true
tokens = (default)IMPORT, STATIC_IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import java.util.Arrays;

import java.util.List; // violation 'Extra separation in import group before 'java.util.List''

import static java.util.Collections.emptyList;

import static java.util.Collections.sort; // violation 'Extra separation in import group'

import com.google.common.collect.Lists;

import static com.google.common.collect.Lists.asList;

public class InputImportOrderUnderSeparatedSameTypeBlankLine {
}
