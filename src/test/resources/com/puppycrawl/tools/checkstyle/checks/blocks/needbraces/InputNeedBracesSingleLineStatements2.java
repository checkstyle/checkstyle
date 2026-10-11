/*
NeedBraces
allowSingleLineStatement = true
allowEmptyLoopBody = (default)false
tokens = (default)LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesSingleLineStatements2
{
    private int getSomething(int num)
    {
        int counter = 0;
        switch (num) {
            case 1: counter++; break;
            case 2:
                counter += 2;
                break;
            case 3:
                counter += 3;
                break;
            case 6: counter += 10; break;
            default: counter = 100; break;
        }
        return counter;
    }

    private void method(){
        if(false) {
            switch (0) {
                case -1:
                    return;
                default:
                    return;
            }
        }
        switch(1){
            case 1: return;
            default: throw new RuntimeException("");
        }
    }
}
