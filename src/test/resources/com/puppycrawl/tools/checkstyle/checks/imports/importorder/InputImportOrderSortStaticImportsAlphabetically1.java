package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

/*
 * Config:
 * option = top
 * groups = {java, javax, org}
 * ordered = true
 * separated = true
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
import static org.junit.Assert.fail; // ok
import static javax.xml.transform.TransformerFactory.newInstance; // ok
import static java.lang.Math.cos; // ok
import static java.lang.Math.abs; // ok

public class InputImportOrderSortStaticImportsAlphabetically1 {
}
