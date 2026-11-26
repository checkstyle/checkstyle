/*
ImportOrder
option = top
groups = (default)
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrdering = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)IMPORT,STATIC_IMPORT

*/

// non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import lombok.val;
import lombok.experimental.FieldDefaults; // violation 'Wrong lexicographical order for 'lombok.experimental.FieldDefaults' import. Should be before 'lombok.val'.'

public class InputImportOrderEclipseType2 {
}
