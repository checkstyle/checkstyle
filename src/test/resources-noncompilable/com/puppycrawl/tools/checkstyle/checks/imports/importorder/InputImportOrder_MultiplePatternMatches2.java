//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

/*
 * Config:
 * option = bottom
 * groups = {/^javax\./, com}
 * ordered = true
 * separated = true
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
import java.io.File; // ok

import org.*; // violation
import org.myOrgorgan.Test; // ok

public class InputImportOrder_MultiplePatternMatches2 {
}
