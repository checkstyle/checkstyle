/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputUnusedLocalVariableNestedClasses3 {
    void method() {
        final OuterClass outerClass = new OuterClass();
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
        @A Outer p1 = new @A Outer();
        // violation below 'Unused local variable 'p2'.'
        @A Outer.@B Inner p2 = p1.new @B Inner();
    }

    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    @interface A { }

    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    @interface B { }
}

class H006_ComplexConstructors<T> {
    public <V> H006_ComplexConstructors(T t, V v) { }

    class Inner3 {
        Inner3(int x) {
            H006_ComplexConstructors<Integer> instance =
                    new <String>H006_ComplexConstructors<Integer>(0, "");
            // violation below 'Unused local variable 'o'.'
            Object o = instance.new Inner3(5).new <String>InnerInner3("hey");
        }

        class InnerInner3 {
            <D> InnerInner3(D in) { }
        }
    }
}

class NullQualifiedNew2 {
    class Inner {
        Inner(int i) {}
    }

    public static void main(String[] args) {
        int i = 1;
        a: try {
            NullQualifiedNew2 c = null;
            c.new Inner(i++) {};
        } catch (NullPointerException e) {
            break a;
        }
        if (i != 1) throw new Error("i = " + i);
    }
}

class T7090499<E> {

    static class B<X> { }

    class A<X> {
        class X { }

        class Z<Y> { }
    }

    T7090499 t = new T7090499() {
        void test(Object arg1, B arg2) {
            // violation below 'Unused local variable 'b'.'
            boolean b = arg1 instanceof A;
            // violation below 'Unused local variable 'a'.'
            Object a = (A) arg1;
            A a2 = new A() { };
            a2.new Z() { };
        }
    };
}
