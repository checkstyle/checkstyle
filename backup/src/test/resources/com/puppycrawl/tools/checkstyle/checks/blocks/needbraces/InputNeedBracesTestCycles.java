/*
NeedBraces
allowSingleLineStatement = true
allowEmptyLoopBody = (default)false
tokens = LITERAL_WHILE, LITERAL_DO, LITERAL_FOR


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesTestCycles
{
    public void method() {
        if (false)
            while(true)
            {

            }

        if (true)
            do {
                int a = 1;
            }
            while(true);

        if (true)
            for(;;){
             assert true;}

        if (true){

        }else {

        }

        switch(1) {
            case 1: {
                break;
            }
        }

        switch(1) {
        case 1:  System.lineSeparator();
        case 2: { break;}

        case 3: {
            break;}

        case 4: // ok
            break;

        case 5:  System.lineSeparator(); // ok
            break;
        }

        switch(1) {
            default:
            {

            }
        }

        switch(1) {
        default: // ok
         break;
    }

    }
}
