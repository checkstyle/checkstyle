/*
MultipleStringLiterals
allowedDuplicates = 2
ignoreStringsRegexp = (default)^""$
ignoreOccurrenceContext = (default)ANNOTATION


*/

package com.puppycrawl.tools.checkstyle.checks.coding.multiplestringliterals;

public class InputMultipleStringLiteralsNoWarnings { // ok
    private final String m4 = "" + "";
}
