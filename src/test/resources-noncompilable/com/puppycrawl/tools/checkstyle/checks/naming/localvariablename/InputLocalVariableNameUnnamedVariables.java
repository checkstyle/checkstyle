/*
LocalVariableName
allowOneCharVarInForLoop = (default)false
format = (default)^([a-z][a-zA-Z0-9]*|_)$
tokens = (default)VARIABLE_DEF


*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.naming.localvariablename;

import java.util.PriorityQueue;
import java.util.Queue;

public class InputLocalVariableNameUnnamedVariables {

    public void testLocalVariables() {
        Queue<Integer> q = new PriorityQueue<>();
        int __ = 0;   // violation, 'Name '__' must match pattern*'
        int _result = 0; // violation, 'Name '_result' must match pattern*'
        q.add(1);
        q.add(2);
        for (Integer element : q) {
            var _ = q.poll();   // ok, unnamed variable
            var _ = q.poll();
        }
    }

    public void testLocalVariablesInForLoops() {
        Queue<Integer> q = new PriorityQueue<>();

        for (Integer _ : q) {
            var x1 = q.poll();
            var x2 = q.poll();
        }
        for (Integer __ : q) {  // violation, 'Name '__' must match pattern*'
            var x1 = q.poll();
            var x2 = q.poll();
        }

        for (int ab = 0, _ = Integer.valueOf(1); ab < 3; ab++) { }
    }
}
