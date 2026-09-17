/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

// violation below 'Only one statement per line allowed.'
import static java.lang.Math.PI; import static
        java.util.Collections.sort;

// violation below 'Only one statement per line allowed.'
import java.util.List; import
        java.util.ArrayList;

public class InputOneStatementPerLineImportStatements {
    // violation below 'Only one statement per line allowed.'
    int a = 2; int
      b = 3;
}
