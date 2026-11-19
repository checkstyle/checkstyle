/*
ImportOrder
option = (default)under
groups = java, javax, org
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

import java.io.File;
import module java.base;

import javax.swing.JTable; // ok, blank line after module import

import module java.sql;
import org.example.Foo; // ok, blank line before module import

public class InputImportOrderModuleImportBetweenGroupsSeparated {
}
