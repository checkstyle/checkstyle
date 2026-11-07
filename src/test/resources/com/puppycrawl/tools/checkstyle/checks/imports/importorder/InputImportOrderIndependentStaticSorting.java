/*
ImportOrder
option = top
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

import static java.lang.Math.min;
import static java.lang.Math.max; // violation 'Wrong lexicographical order'

import java.util.Set;
import java.util.List;

public class InputImportOrderIndependentStaticSorting {
}
