/*
UnusedImports
processJavadoc = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class InputUnusedImportsWithBlockMethodParameters { // ok

/**
 * @see ExecutorService#invokeAll(Collection, long, TimeUnit)
 */
    public int calculate() {
        return 0;
    }
}
