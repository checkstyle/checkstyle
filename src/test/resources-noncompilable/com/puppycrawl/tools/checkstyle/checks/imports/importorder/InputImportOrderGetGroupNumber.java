//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import javax.io.File; // ok
import sun.tools.util.ModifierFilter.ALL_ACCESS; // ok
import java.test.javax; // ok
import org.junit.Test; // ok

/*
 * Config:
 * option = under
 * groups = {/javax/, sun, /^java/, org, /java/}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
public class InputImportOrderGetGroupNumber {
}

