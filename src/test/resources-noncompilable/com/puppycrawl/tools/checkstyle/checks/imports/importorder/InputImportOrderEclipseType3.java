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
useContainerOrdering = true
useContainerOrderingForStatic = (default)false
tokens = (default)IMPORT,STATIC_IMPORT

*/

// non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import lombok.experimental.FieldDefaults;
import lombok.val; // violation 'Wrong lexicographical order for 'lombok.val' import. Should be before 'lombok.experimental.FieldDefaults'.'
import lombok.var;
import lombok.AccessLevel; // violation 'Wrong lexicographical order for 'lombok.AccessLevel' import. Should be before 'lombok.var'.'

public class InputImportOrderEclipseType3 {
}
