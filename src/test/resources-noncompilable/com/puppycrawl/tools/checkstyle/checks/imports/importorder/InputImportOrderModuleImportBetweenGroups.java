/*
ImportOrder
option = (default)under
groups = java, org
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

import java.util.List;
import module java.base;
import org.example.Foo; // violation ''org.example.Foo' should be separated from previous imports.'

public class InputImportOrderModuleImportBetweenGroups {
}
