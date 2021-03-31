//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import java.util.java.io.File; // ok
import java.io.File; // ok

/*
 * Config:
 * option = under
 * groups = {/java.util/, /java.io/}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
public class InputImportOrderSimilarGroupPattern {

}
