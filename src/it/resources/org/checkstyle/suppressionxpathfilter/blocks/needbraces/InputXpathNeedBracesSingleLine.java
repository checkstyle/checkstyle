/*
NeedBraces
allowEmptyLoopBody = (default)false
allowSingleLineStatement = true
tokens = (default)LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE


*/

package org.checkstyle.suppressionxpathfilter.blocks.needbraces;

import com.puppycrawl.tools.checkstyle.checks.blocks.needbraces.InputNeedBracesSingleLineStatements;

public class InputXpathNeedBracesSingleLine {
    private static class SomeClass {
        boolean flag = true;
        private static boolean test(boolean k) {
            return k;
        }
    }

    /** Test do/while loops **/
    public int test() {
        // Invalid
        if (SomeClass.test(true) == true) // violation ''if'' construct must use '{}'s.'
            return 4;
        return 0;
    }
}
