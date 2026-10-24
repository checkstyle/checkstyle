/*
NeedBraces
allowEmptyLoopBody = (default)false
allowSingleLineStatement = (default)false
tokens = (default)LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE


*/

package org.checkstyle.suppressionxpathfilter.blocks.needbraces;

public class InputXpathNeedBracesDo {
    /** @return helper func **/
    boolean condition()
    {
        return false;
    }

    /** Test do/while loops **/
    public void test() {
        // Invalid
        do test(); while (condition()); // violation ''do'' construct must use '{}'s.'
    }
}
