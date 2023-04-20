/*
FinalLocalVariable
validateEnhancedForLoopVariable = true
tokens = PARAMETER_DEF,VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableCheckNestedScopes {
    public int NestedScopes(int num) { // violation 'Variable 'num' should be declared final.'
        int result = 0;

        if (num > 0) {
            int a;
            result =num + 1;
            if(result%2==1){
               int p; // violation 'Variable 'p' should be declared final.'
               a=2;
               p=a/2;
            } else {
                int p; // violation 'Variable 'p' should be declared final.'
                a=3;
                p=a/2;
            }
            a=4;
            result=a%2;
        }

        switch (num) {
            case 1:
                int x; // violation 'Variable 'x' should be declared final.'
                x = 1;
                break;
            case 2:
                int y; // violation 'Variable 'y' should be declared final.'
                y = 2;
                break;
            default:
                int z; // violation 'Variable 'z' should be declared final.'
                if (num>2) {
                    int b; // violation 'Variable 'b' should be declared final.'
                    z = 3;
                    b = z+4;
                }
        }

        return result;
    }

}
