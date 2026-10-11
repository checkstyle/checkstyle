/*
NeedBraces
allowSingleLineStatement = (default)false
allowEmptyLoopBody = (default)false
tokens = (default)LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesLoopBodyFalse2 {

    private int value;

    public void foo12() {
        for (int i = 0; incrementValue() < 8; i++) {
            int a = 5;
        }
    }

    public void foo18() {
        if(true) {
            int a;
        }
    }

    private int incrementValue() {
        return value++;
    }

    private void decrementValue() {
        value--;
    }
}
