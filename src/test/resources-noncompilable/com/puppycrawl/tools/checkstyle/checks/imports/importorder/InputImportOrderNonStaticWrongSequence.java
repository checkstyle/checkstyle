/*
ImportOrder
option = bottom
groups = (default)
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
import java.util.LinkedList; // ok
import java.util.HashMap; // violation 'Wrong order for 'java.util.HashMap' import.'

class InputImportOrderNonStaticWrongSequence {
}


