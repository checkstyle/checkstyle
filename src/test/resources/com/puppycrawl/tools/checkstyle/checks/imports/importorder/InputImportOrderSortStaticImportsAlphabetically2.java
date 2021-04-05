package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import static org.junit.Assert.fail; // ok
import static javax.xml.transform.TransformerFactory.newInstance; // violation
import static java.lang.Math.cos; // violation
import static java.lang.Math.abs; // violation

/*
 * Config:
 * option = top
 * groups = {java, javax, org}
 * ordered = true
 * separated = true
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = true
 * useContainerOrderingForStatic = false
 */
public class InputImportOrderSortStaticImportsAlphabetically2 {
}
