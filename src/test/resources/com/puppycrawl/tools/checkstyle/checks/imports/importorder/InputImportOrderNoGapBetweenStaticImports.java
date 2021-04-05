package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import static java.lang.Math.abs; // ok
import static java.lang.Math.cos; // ok
import static javax.xml.transform.TransformerFactory.newInstance; // ok
import static org.junit.Assert.fail; // ok

/*
 * Config:
 * option = bottom
 * groups = {java, javax, org}
 * ordered = true
 * separated = true
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
public class InputImportOrderNoGapBetweenStaticImports {
}
