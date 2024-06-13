/*
FinalLocalVariable
validateUnnamedVariables = true
validateEnhancedForLoopVariable = true
tokens = (default)VARIABLE_DEF

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

import java.util.PriorityQueue;
import java.util.Queue;

public class InputFinalLocalVariableValidateUnnamedVariablesTrue {

    public void testUnnamedVariables() {
        final Queue<Integer> q = new PriorityQueue<>();
        q.add(1);
        q.add(2);
        for (Integer i : q) {  // violation
            var _ = q.poll(); // violation
            var __ = q.poll(); // violation
        }
        final int _ = sideEffect();
        int _ = sideEffect(); // violation
        int _result = sideEffect(); // violation
    }

    static
    {
        Runnable _ = new Runnable() // violation
        {
            public void run()
            {
            }
        };
    }

    public void testInEnhancedFor() {
        final int[] squares = {0, 1, 4, 9, 16, 25};
        for (final int i : squares) {
        }
        for (int _ : squares) { // violation

        }
        for (final int _ : squares) {

        }
        for (int __ : squares) { // violation

        }
    }

    public int sideEffect() {
        return 0;
    }
}
