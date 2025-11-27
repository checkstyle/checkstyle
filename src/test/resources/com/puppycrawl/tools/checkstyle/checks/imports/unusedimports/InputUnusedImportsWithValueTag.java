/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Calendar;

public class InputUnusedImportsWithValueTag {

    /**
     * Method determines current month as for {@value Calendar#MONTH}.
     *
     * @return index of the current month.
     */
    public int currentMonth() {
        return 1;
    }
}
