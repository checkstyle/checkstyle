/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableNestedClasses3 {
    void method() {
        final OuterClass outerClass = new OuterClass();
        final OuterClass.InnerClass innerClass = outerClass.new InnerClass(); // ok
        System.out.println(innerClass);
    }

    class OuterClass {
        class InnerClass {
            InnerClass() { }
        }
    }
}
