/*
ImportOrder
option = top
groups = java
ordered = (default)true
separated = true
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)IMPORT,STATIC_IMPORT


*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import static java.lang.Math.abs;
import module java.base;
import java.util.List; // violation ''java.util.List' should be separated from previous imports.'

public class InputImportOrderModuleImportStaticTop {
}
