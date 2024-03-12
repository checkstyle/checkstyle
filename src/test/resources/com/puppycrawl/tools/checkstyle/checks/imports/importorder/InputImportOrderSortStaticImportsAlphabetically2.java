/*
ImportOrder
option = top
groups = java, javax, org
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

import static org.junit.Assert.fail;
import static javax.xml.transform.TransformerFactory.newInstance; // violation 'Wrong order for .* import.'
import static java.lang.Math.cos; // violation 'Wrong order for 'java.lang.Math.cos' import.'
import static java.lang.Math.abs; // violation 'Wrong order for 'java.lang.Math.abs' import.'

public class InputImportOrderSortStaticImportsAlphabetically2 {
}
