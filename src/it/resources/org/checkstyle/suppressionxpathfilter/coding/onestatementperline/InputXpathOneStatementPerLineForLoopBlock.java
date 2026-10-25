/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.onestatementperline;

public class InputXpathOneStatementPerLineForLoopBlock {
    private void foo5(int var1, int var2) {
        for(int n = 0,
            k = 1
            ; n<5
                ;
            // violation below 'Only one statement per line allowed.'
            n++, k--) { var1++; var2++; }
    }
}
