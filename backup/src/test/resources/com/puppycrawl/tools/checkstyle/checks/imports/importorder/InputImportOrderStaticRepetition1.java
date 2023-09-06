/*
ImportOrder
option = top
groups = java, org
ordered = (default)true
separated = true
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = true
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import static java.lang.Math.*; // ok
import static org.antlr.v4.runtime.CommonToken.*; // ok
import static org.antlr.v4.runtime.CommonToken.*;  // ok

import java.util.Set; // ok

import org.junit.Test; // ok

public class InputImportOrderStaticRepetition1 {
}
