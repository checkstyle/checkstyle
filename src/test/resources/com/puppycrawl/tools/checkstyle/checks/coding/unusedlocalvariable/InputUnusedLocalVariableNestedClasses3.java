/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputUnusedLocalVariableNestedClasses3 {
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

class Outer {
    public class Inner { }
}

class Test {
    void m() {
        @A Outer p1 = new @A Outer(); // ok
        @A Outer.@B Inner p2 = p1.new @B Inner(); // violation
    }
}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface A { }

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface B { }

class H006_ComplexConstructors<T> {
    public <V> H006_ComplexConstructors(T t, V v) { }

    class Inner3 {
        Inner3(int x) {
            H006_ComplexConstructors<Integer> instance = // ok
                    new <String>H006_ComplexConstructors<Integer>(0, "");
            Object o = instance.new Inner3(5).new <String>InnerInner3("hey"); // violation
        }

        class InnerInner3 {
            <D> InnerInner3(D in) { }
        }
    }
}
