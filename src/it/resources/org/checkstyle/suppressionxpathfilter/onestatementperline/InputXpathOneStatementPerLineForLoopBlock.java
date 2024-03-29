package org.checkstyle.suppressionxpathfilter.onestatementperline;

public class InputXpathOneStatementPerLineForLoopBlock {
    private void foo5(int var1, int var2) {
        for(int n = 0,
            k = 1
            ; n<5
                ;
            n++, k--) { var1++; var2++; } //warn
    }
}
