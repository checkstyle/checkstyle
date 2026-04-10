/*
NeedBraces
allowSingleLineStatement = true
allowEmptyLoopBody = true
tokens = LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE, \
         LITERAL_CASE, LITERAL_DEFAULT, LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

class InputNeedBracesTestItWithAllowsOn2
{
    /** @return helper func **/
    boolean condition()
    {
        return false;
    }

    void whitespaceAfterSemi()
    {
        //reject
        int i = 1;int j = 2;

        //accept
        for (;;) {
        }
    }

    /** Empty constructor block. **/
    public InputNeedBracesTestItWithAllowsOn2() {}

    /** Empty method block. **/
    public void emptyImplementation() {}
}
