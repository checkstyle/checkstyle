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
import static javax.xml.transform.TransformerFactory.newInstance; // violation 'Wrong lexicographical order for 'javax.xml.transform.TransformerFactory.newInstance' import. Should be before 'org.junit.Assert.fail'.'
import static java.lang.Math.cos; // violation 'Wrong lexicographical order for 'java.lang.Math.cos' import. Should be before 'javax.xml.transform.TransformerFactory.newInstance'.'
import static java.lang.Math.abs; // violation 'Wrong lexicographical order for 'java.lang.Math.abs' import. Should be before 'java.lang.Math.cos'.'

public class InputImportOrderSortStaticImportsAlphabetically2 {
}
