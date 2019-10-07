package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlySame {
    static {
    }

    public InputRightCurlySame() {
        Thread t = new Thread(new Runnable() {
            {
            }

            @Override
            public void run() {
            }
        });
    }

    public void doLoop() {
        do {
        } while (true);
    }

    public void whileLoop() {
        while (true) {
        }
    }

    public void forLoop() {
        for (; ; ) {
        }
    }

    public void function() {
    }

    ;

    public class TestClass {
    }

    ;

    public void testMethod() {
    }

    ;

    public @interface TestAnnotation {
    }

    public @interface TestAnnotation1 {
        String someValue();
    }

    public @interface TestAnnotation3 {
        String someValue();
    }

    public @interface TestAnnotation4 {
        String someValue();
    }
}
