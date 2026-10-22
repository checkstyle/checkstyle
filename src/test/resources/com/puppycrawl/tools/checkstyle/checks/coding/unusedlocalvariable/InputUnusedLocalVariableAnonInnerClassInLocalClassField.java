/*
UnusedLocalVariable
allowUnnamedVariables = false
jdkVersion = (default)22

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableAnonInnerClassInLocalClassField {

    static class Base {
        int a;
    }

    void method() {
        // false negative, anon inner class in a field of a local class is not
        // re-checked, so 'a' below is considered as used
        int a = 1;
        class Local {
            Object field = new Base() {
                void test() {
                    System.out.println(a);
                }
            };
        }
    }
}
