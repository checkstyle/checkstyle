/*
GoogleRightCurly

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

class InputGoogleRightCurlyTypes {
    private String name;

    interface Worker {
        void work();}
    // violation above ''}' at column 21 should be alone on a line'

   interface Shape {
        int sides();

        default boolean valid() {
            return sides() > 0;
    }}
        // 2 violations above:
        // ''}' at column 5 should be alone on a line'
        // ''}' at column 6 should be alone on a line'

    @interface Label {
        String value();}
    // violation above ''}' at column 24 should be alone on a line'

    @interface Author {
        String name();
        // violation below ''}' at column 33 should be alone on a line'
        int version() default 1;} record Point3D(int x, int y, int z) {
        int sum() {
            return x + y + z;
        }
    }

    @interface Label2 {
        String value();
    };
    // violation above ''}' at column 5 should be alone on a line'

    class Calculator {
        int add(int a, int b) {
            return a + b;
        }
    }

    // violation 3 lines below ''}' at column 5 should be alone on a line'
    interface Reader {
        String read();
    } record Employee(int id, String name) {
        String display() {
            return id + ":" + name;
        }
    }

    Worker anonymousWorker = new Worker() {
        @Override
        public void work() {
            int work = 1;
        }
    };

    class Inner {}

    class Inner2 {
    };
    // violation above ''}' at column 5 should have line break after'

    record Inner3() {
    };
    // violation above ''}' at column 5 should have line break after'

    class Inner4 {
        int h = 1;
    };
    // violation above ''}' at column 5 should be alone on a line'

    interface Foo {
        void foo();
    };
    // violation above ''}' at column 5 should be alone on a line'

    enum Day {
        MONDAY
    };
    // violation above ''}' at column 5 should be alone on a line'

    record Foo2() {
        static int a = 1;
    };
    // violation above ''}' at column 5 should be alone on a line'

    // violation below 'Empty block should be concise {}'
    class Inner5 {   }

    class Inner6 {
    }

    enum Color {RED, YELLOW, GREEN, BLACK}

    enum Color2 {RED, YELLOW, GREEN, BLACK,}

    class Inner7 { /* Comment */ }
    // violation above ''}' at column 34 should be alone on a line'
}

class SomeClass {} // comment
