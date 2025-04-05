/*
LocalVariableName
format = (default)^([a-z][a-zA-Z0-9]*|_)$
allowOneCharVarInForLoop = (default)false

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.naming.localvariablename;

import java.util.PriorityQueue;
import java.util.Queue;

public class InputLocalVariableNameUnnamedVariables {

    public void testLocalVariables() {
        Queue<Integer> q = new PriorityQueue<>();
        int __ = 0;   // violation
        int _result = 0; // violation
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
        for (Integer __ : q) {  // violation
            var x1 = q.poll();
            var x2 = q.poll();
        }

        for (int ab = 0, _ = Integer.valueOf(1); ab < 3; ab++) { }
    }
}
