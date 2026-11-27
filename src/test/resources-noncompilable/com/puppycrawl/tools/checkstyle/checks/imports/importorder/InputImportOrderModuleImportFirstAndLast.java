/*
ImportOrder
option = (default)under
groups = (default)
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)IMPORT,STATIC_IMPORT


*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import module java.base;

import java.io.File; // ok, blank line after leading module import is ignored
import java.util.List; // ok, trailing module import is ignored
import module java.sql;

public class InputImportOrderModuleImportFirstAndLast {
}
