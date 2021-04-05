package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import static java.lang.Math.abs; // ok

import static java.lang.Math.cos; // violation

import static org.junit.Assert.assertEquals; // violation
import static org.junit.Assert.fail; // ok

/*
 * Config:
 * option = top
 * groups = {java, org}
 * ordered = true
 * separated = true
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
public class InputImportOrderStaticGroupSeparated {
    void method() {
    }
}
