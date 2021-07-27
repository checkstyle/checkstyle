/*
ImportOrder
option = (default)under
groups = /java/,/rga/,/myO/,/org/,/organ./
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import java.io.File; // ok

import org.*; // violation
import org.myOrgorgan.Test; // ok

public class InputImportOrder_MultiplePatternMatches1 {
}
