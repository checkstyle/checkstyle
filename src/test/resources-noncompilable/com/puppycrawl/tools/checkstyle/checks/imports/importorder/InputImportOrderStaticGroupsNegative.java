//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

/*
 * Config:
 * option = top
 * groups = {}
 * ordered = true
 * separated = true
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {org, java}
 * sortStaticImportsAlphabetically = true
 * useContainerOrderingForStatic = false
 */
import com.google.common.collect.Lists; // ok

import static org.junit.Assert.fail; // violation
import static java.lang.String.format; // ok
import static org.infinispan.test.TestingUtil.extract; // violation

public class InputImportOrderStaticGroupsNegative {
}
