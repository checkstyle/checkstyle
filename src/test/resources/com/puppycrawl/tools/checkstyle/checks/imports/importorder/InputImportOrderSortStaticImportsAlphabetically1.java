/*
ImportOrder
option = top
groups = java, javax, org
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

import static org.junit.Assert.fail; // ok
import static javax.xml.transform.TransformerFactory.newInstance; // ok
import static java.lang.Math.cos; // ok
import static java.lang.Math.abs; // ok

public class InputImportOrderSortStaticImportsAlphabetically1 {
}
