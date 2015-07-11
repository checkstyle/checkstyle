package com.puppycrawl.tools.checkstyle.checks.coding;

/**
 * This file contains test inputs for OneStatementPerLineCheckInput
 * which cause compilation problem in Eclipse 4.2.2 but still
 * must be tested.
 */

/**
 * Two import statements and one 'empty' statement
 * which are not on the same line are legal.
 */
import java.lang.annotation.Annotation;
;
import java.lang.String;

public class OneStatementPerLineCheckInput {
    /**
     * According to java language specifications,
     * statements end with ';'. That is why ';;'
     * may be considered as two empty statements on the same line
     * and rises violation.
     */
    ;; //warn
}
