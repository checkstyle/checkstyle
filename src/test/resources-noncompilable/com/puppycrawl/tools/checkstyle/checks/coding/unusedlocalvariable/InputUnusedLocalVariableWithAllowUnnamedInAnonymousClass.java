/*
UnusedLocalVariable
allowUnnamedVariables = (default)true
jdkVersion = (default)22


*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.util.List;

public class InputUnusedLocalVariableWithAllowUnnamedInAnonymousClass {
    Runnable anonymous() {
        return new Runnable() {
            @Override
            public void run() {
                var _ = "ok";
                String _ = "ok";
                for (String _ : List.of("ok")) {
                }
                var __ = "violation"; // violation 'Unused named local variable '__''
            }
        };
    }

    void besideAnonymous() {
        var _ = "ok";
        Runnable r = new Runnable() {
            @Override
            public void run() {
            }
        };
        r.run();
    }
}
