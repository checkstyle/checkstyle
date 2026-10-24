/*
NeedBraces
allowEmptyLoopBody = (default)false
allowSingleLineStatement = (default)false
tokens = (default)LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE


*/

package org.checkstyle.suppressionxpathfilter.blocks.needbraces;

public class InputXpathNeedBracesEmptyLoopBody {
    private int incrementValue() {
        return 1;
    }

    public void test() {
        while(incrementValue() < 5);; // violation ''while'' construct must use '{}'s.'
    }
}
