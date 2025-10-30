// non-compiled with javac: compilable with Java25

package com.puppycrawl.tools.checkstyle.grammar.java25;

class InputFlexibleConstructorBodyWithNestedClass {

    int i;

    public void hello() {
        System.out.println("Hello");
    }

    class Inner {

        int j;

        Inner() {
            var x = i;
            var y = InputFlexibleConstructorBodyWithNestedClass.this.i;
            hello();
            InputFlexibleConstructorBodyWithNestedClass.this.hello();
            super();
        }
    }
}
