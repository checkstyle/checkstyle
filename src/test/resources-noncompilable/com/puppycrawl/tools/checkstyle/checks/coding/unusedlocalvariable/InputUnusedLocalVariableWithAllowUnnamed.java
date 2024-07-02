/*
UnusedLocalVariable
allowUnnamedVariables = (default)true

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.util.PriorityQueue;
import java.util.Queue;

public class InputUnusedLocalVariableWithAllowUnnamed {
    void m() {
        Queue<Integer> q = new PriorityQueue<>();
        q.add(1);
        q.add(3);
        q.add(4);
        while (!q.isEmpty()) {
            var _x = q.poll();  // violation, 'Unused named local variable '_x''
            var __ = q.poll(); // violation, 'Unused named local variable '__''
            var _ = q.poll();
        }
    }

    void m2() {
        Queue<Integer> q = new PriorityQueue<>();
        int count = 0;
        q.add(1);
        q.add(3);
        q.add(4);
        for (Integer _ : q) {
            count++;
        }
        for (Integer __ : q) { // violation, 'Unused named local variable '__''
            count++;
        }
        System.out.println(count);
    }

    void m3() {
        for (int i = 0, _ = sideEffect(); i < 10; i++) {
        }
        // violation below, 'Unused named local variable '__''
        for (int i = 0, __ = sideEffect(); i < 10; i++) {
        }
    }

    int sideEffect() {
        return 0;
    }
}
