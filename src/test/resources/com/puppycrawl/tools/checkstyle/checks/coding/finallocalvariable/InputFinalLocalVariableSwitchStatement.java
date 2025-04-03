/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF
validateUnnamedVariables = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableSwitchStatement {

    enum T {
        A, B, C;
    }

    public void foo() {
        int x;

        {
            final T y = T.A;

            switch (y) {
                case A:
                    x = 0;
                case B:
                    x = 0;
                case C:
                    x = 0;
            }

        }

        {
            final T y = T.A;

            switch (y) {
                case A:
                    x = 1;
                case B:
                    x = 1;
                case C:
                    x = 1;

            }
        }
    }
}
