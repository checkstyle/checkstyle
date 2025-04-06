/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)VARIABLE_DEF
validateUnnamedVariables = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

import java.util.stream.Stream;

public class InputFinalLocalVariableLeavingSlistToken {

    private int assignedInInstanceInitAndCtor;
    private static int assignedInStaticInitAndFoo1;

    {
        assignedInInstanceInitAndCtor = 1;
    }

    static {
        assignedInStaticInitAndFoo1 = 1;
    }

    InputFinalLocalVariableLeavingSlistToken() {
        assignedInInstanceInitAndCtor = 2;
    }

    void foo1() {
        assignedInStaticInitAndFoo1 = 2;
    }

    void foo2() {
        int a;
        if (true) {
        }
        else {
            a = 1;

            try {
            } catch (Exception e) {
            } finally {
            }

            if (true) {
            }

            Stream.of(1).forEach(integer -> {});

            synchronized (InputFinalLocalVariableLeavingSlistToken.class) {
            }

            for (int i = 0; i < 0; ++i) {
            }
            while (Math.random() > 0) {
            }
            do {
            }
            while (Math.random() > 0);

            {}

            a = 2;
        }
    }
}
