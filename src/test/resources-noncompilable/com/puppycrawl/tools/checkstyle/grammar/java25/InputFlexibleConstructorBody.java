// Java25
package com.puppycrawl.tools.checkstyle.grammar.java25;

public class InputFlexibleConstructorBody {

    public InputFlexibleConstructorBody() {
        System.out.println("hello");
        super(); // no change introduced
    }
}

class Jep512 {
    int i;
    String[] arr;

    Jep512() {
    }

    public int size() {
        return arr.length;
    }

    public boolean isEmpty() {
        return arr.length == 0;
    }
}

class Outer extends Jep512 {

    int i;
    String s = "hello";

    Outer(int number) {
        if (number > 0) {
            throw new IllegalArgumentException("number must be positive");
        }
        i = number;
        super();
    }

    Outer() {}

    public int size() { return super.size(); }

    public boolean isEmpty() { return Outer.super.isEmpty(); }

    class Inner {
        public Inner() {
        }
    }
}

class Derived extends Outer.Inner {
    public Derived(Outer s) {
        // SUPER_CTOR
        s.super();
    }
    public Derived() {
        // SUPER_CTOR
        new Outer().super();
    }

    public Derived(int arg) {
        // SUPER_CTOR
    new Outer().super(
    arg
    + 1L);
    }
}
