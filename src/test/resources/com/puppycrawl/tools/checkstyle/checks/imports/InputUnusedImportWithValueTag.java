package com.puppycrawl.tools.checkstyle.checks.imports;

import java.util.Calendar;

public class InputUnusedImportWithValueTag {

    /**
     * Method determines current month as for {@value Calendar#MONTH}.
     *
     * @return index of the current month.
     */
    public int currentMonth() {
        return 1;
    }
}
