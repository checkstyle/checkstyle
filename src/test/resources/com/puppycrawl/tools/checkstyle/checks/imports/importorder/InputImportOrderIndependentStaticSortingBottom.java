/*
ImportOrder
option = bottom
groups = (default)
ordered = false
separated = true
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = true
useContainerOrderingForStatic = (default)false
tokens = (default)IMPORT,STATIC_IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import java.util.Set;
import java.util.List;

import static java.lang.Math.min;
import static java.lang.Math.max; // violation 'Wrong lexicographical order'

public class InputImportOrderIndependentStaticSortingBottom {
}
