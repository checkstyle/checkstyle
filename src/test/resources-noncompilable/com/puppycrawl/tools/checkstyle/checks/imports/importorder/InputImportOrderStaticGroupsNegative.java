/*
ImportOrder
option = top
groups = (default)
ordered = (default)true
separated = true
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = org, java
sortStaticImportsAlphabetically = true
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import com.google.common.collect.Lists;

import static org.junit.Assert.fail; // violation 'Wrong order for 'org.junit.Assert.fail' import.'
import static java.lang.String.format;
import static org.infinispan.test.TestingUtil.extract; // violation 'Wrong order for 'org.infinispan.test.TestingUtil.extract' import.'

public class InputImportOrderStaticGroupsNegative {
}
