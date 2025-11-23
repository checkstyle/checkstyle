/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class InputUnusedImportsWithBlockMethodParameters {

/**
 * @see ExecutorService#invokeAll(Collection, long, TimeUnit)
 */
    public int calculate() {
        return 0;
    }
}
