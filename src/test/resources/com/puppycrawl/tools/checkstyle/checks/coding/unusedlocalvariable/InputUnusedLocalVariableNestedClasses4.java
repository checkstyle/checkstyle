/*
UnusedLocalVariable
severity = warning


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputUnusedLocalVariableNestedClasses4 {
    void method() {
        final OuterClass outerClass = new OuterClass(); // ok
        final OuterClass.InnerClass innerClass = outerClass.new InnerClass();
        System.out.println(innerClass);
    }

    class OuterClass {
        class InnerClass {
            InnerClass() { }
        }
    }


}

class Outer2 {
    public class Inner { }
}

class Test2 {
    void m() {
        @A Outer p1 = new @A Outer(); // ok
        @A Outer.@B Inner p2 = p1.new @B Inner();
    }

    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    @interface A { }

    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    @interface B { }
}
