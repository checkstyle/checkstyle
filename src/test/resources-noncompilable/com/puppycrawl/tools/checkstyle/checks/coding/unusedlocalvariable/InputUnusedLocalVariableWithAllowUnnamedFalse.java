/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.util.PriorityQueue;
import java.util.Queue;

public class InputUnusedLocalVariableWithAllowUnnamedFalse {
    void m() {
        Queue<Integer> q = new PriorityQueue<>();
        q.add(1);
        q.add(3);
        q.add(4);
        while (!q.isEmpty()) {
            var _x = q.poll();  // violation, 'Unused local variable '_x''
            var __ = q.poll(); // violation, 'Unused local variable '__''
            var _ = q.poll(); // violation, 'Unused local variable '_''
        }
    }

    void m2() {
        Queue<Integer> q = new PriorityQueue<>();
        int count = 0;
        q.add(1);
        q.add(3);
        q.add(4);
        for (Integer _ : q) { // violation, 'Unused local variable '_''
            count++;
        }
        for (Integer __ : q) { // violation, 'Unused local variable '__''
            count++;
        }
        System.out.println(count);
    }

    void m3() {
        // violation below, 'Unused local variable '_''
        for (int i = 0, _ = sideEffect(); i < 10; i++) {
        }
        // violation below, 'Unused local variable '__''
        for (int i = 0, __ = sideEffect(); i < 10; i++) {
        }
    }

    int sideEffect() {
        return 0;
    }
}
