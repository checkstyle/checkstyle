/*
ImportOrder
option = top
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

import static java.lang.Math.abs; // ok

import static java.lang.Math.cos; // violation

import static org.junit.Assert.assertEquals; // violation
import static org.junit.Assert.fail; // ok

public class InputImportOrderStaticGroupSeparated {
    void method() {
    }
}
