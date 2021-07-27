/*
ImportOrder
option = (default)under
groups = java, javax, org
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import java.io.File; // ok
import java.io.IOException; // ok
import java.util.Iterator; // ok

import com.puppycrawl.tools.checkstyle.checks.imports.importorder.InputImportOrderBug; // violation

public class InputImportOrder_WildcardUnspecified {
}
