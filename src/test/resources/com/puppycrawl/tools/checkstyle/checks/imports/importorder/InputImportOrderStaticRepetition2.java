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
tokens = (default)STATIC_IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import static java.lang.Math.*; // ok
import static org.antlr.v4.runtime.CommonToken.*; // violation '.* should be separated from previous imports.'
import static org.antlr.v4.runtime.CommonToken.*;  // ok

import java.util.Set; // violation 'Wrong order for 'java.util.Set' import.'

import org.junit.Test; // ok

public class InputImportOrderStaticRepetition2 {
}
