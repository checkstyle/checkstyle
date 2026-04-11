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

import static java.lang.Math.*;
import static org.antlr.v4.runtime.CommonToken.*; // violation 'org.antlr.v4.runtime.CommonToken.*' should be separated from previous imports.'
import static org.antlr.v4.runtime.CommonToken.*;

import java.util.Set; // violation 'Import statement for 'java.util.Set' violates the configured import group order.'

import org.junit.Test;

public class InputImportOrderStaticRepetition2 {
}
