/*
NeedBraces
allowSingleLineStatement = (default)false
allowEmptyLoopBody = (default)false
tokens = (default)LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

class InputNeedBracesTestIt2
{
    void whitespaceAfterSemi()
    {
        //reject
        int i = 1;int j = 2;

        //accept
        for (;;) {
        }
    }
}
