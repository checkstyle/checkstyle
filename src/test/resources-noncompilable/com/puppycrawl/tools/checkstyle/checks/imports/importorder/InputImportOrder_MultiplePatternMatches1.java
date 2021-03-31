//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

/*
 * Config:
 * option = under
 * groups = {/java/, /rga/, /myO/, /org/, /organ./}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
import java.io.File; // ok

import org.*; // violation
import org.myOrgorgan.Test; // ok

public class InputImportOrder_MultiplePatternMatches1 {
}
