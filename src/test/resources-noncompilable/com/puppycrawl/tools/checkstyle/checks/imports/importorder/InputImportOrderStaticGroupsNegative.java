//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
/**
 * This test-input is intended to be checked using following configuration:
 *
 * staticGroups = {org, java}
 * option = top
 * ordered = true
 * separated = true
 * sortStaticImportsAlphabetically = true
 * useContainerOrderingForStatic = false
 *
 */
import com.google.common.collect.Lists;

import static org.junit.Assert.fail; // violation: wrong order
import static java.lang.String.format;
import static org.infinispan.test.TestingUtil.extract; // violation: wrong order

public class InputImportOrderStaticGroupsNegative {
}
