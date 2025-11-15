/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.util.ArrayList;
import java.util.List;

public class InputUnusedLocalVariableLambdaExpression2 {

    public void method1() {
        String outer = "outer"; // violation, 'Unused local variable'
        Runnable r = () -> { // violation, 'Unused local variable'
            // violation below, 'Unused local variable'
            List<String> l = new ArrayList<String>() {
                @Override
                public boolean add(String s) {
                    String inner1 = "a";
                    String inner2 = "b";
                    String inner3 = "c"; // violation, 'Unused local variable'
                    return inner1.equals(inner2);
                }
            };
        };
    }

    Runnable r2 = () -> new Runnable() {
        @Override
        public void run() {
            String inner4 = "abc"; // violation, 'Unused local variable'
        }
    };

    static {
        // violation below, 'Unused local variable'
        Runnable r3 = () -> new Runnable() {
            @Override
            public void run() {
                String inner5 = "test"; // violation, 'Unused local variable'
            }
        };
    }
}
