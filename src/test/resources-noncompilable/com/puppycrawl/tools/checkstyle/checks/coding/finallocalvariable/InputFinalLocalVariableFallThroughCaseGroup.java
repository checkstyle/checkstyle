/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFallThroughCaseGroup {
    void foo() {
        int a; // ok
        final int field = switch ((int) Math.random()) {
            case 1, 2:
                a = 1;
            default:
                a = 2;
                yield 3;
        };
    }

    void foo2() {
        int a; // ok
        final int field = switch ((int) Math.random()) {
            case 1:
            case 2:
                a = 1;
            default:
                a = 2;
                yield 3;
        };
    }

    void foo3() {
        int a;
        final int field = switch ((int) Math.random()) {
            case 1:
                a = 1;
                yield 1;
            case 2:
                a = 4;
            default:
                a = 2;
                yield 3;
        };
    }

    void foo4() throws Exception {
        int a; // ok
        int b; // violation 'Variable .* should be declared final.'
        final int field = switch ((int) Math.random()) {
            case 1:
                a = 0;
                b = 0;
                yield 1;
            case 2:
            case 3:
                a = 2;
            default:
                a = 2;
                b = 0;
                yield 3;
        };
    }
}
