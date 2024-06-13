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
        for (Integer i : q) {  // violation,'Variable 'i' should be declared final'
            var _ = q.poll(); // violation,'Variable '_' should be declared final'
            var __ = q.poll(); // violation,'Variable '__' should be declared final'
        }
        final int _ = sideEffect();
        int _ = sideEffect(); // violation,'Variable '_' should be declared final'
        int _result = sideEffect(); // violation,'Variable '_result' should be declared final'
    }

    static
    {
        Runnable _ = new Runnable() // violation,'Variable '_' should be declared final'
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
        for (int _ : squares) { // violation,'Variable '_' should be declared final'

        }
        for (final int _ : squares) {

        }
        for (int __ : squares) { // violation,'Variable '__' should be declared final'

        }
    }

    public int sideEffect() {
        return 0;
    }
}
