/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = VARIABLE_DEF, PARAMETER_DEF


*/

//non-compiled with javac: Compilable with Java20
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

import java.util.List;

public class InputFinalLocalVariableCheck3 {

    record StringInteger(String text, Integer number) {
    }

    // violation below 'Variable 'list' should be declared final'
    public static void simple(List<StringInteger> list) {
        for (StringInteger(String text, Integer number) : list) {
            System.out.println(number);
            System.out.println(number);
            System.out.println(text);
            System.out.println(text);
            System.out.println(number);
            System.out.println(number.intValue());
            System.out.println(text);
        }
    }
}
