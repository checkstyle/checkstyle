package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import java.io.File; // ok
import java.io.IOException; // ok
import java.util.Iterator; // ok

import com.puppycrawl.tools.checkstyle.checks.imports.importorder.InputImportOrderBug; // violation

/*
 * Config:
 * option = under
 * groups = {java, javax, org}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
public class InputImportOrder_WildcardUnspecified {
}
