/*
NoLineWrap
tokens = RECORD_DEF, CLASS_DEF, CTOR_DEF, COMPACT_CTOR_DEF
skipAnnotations = (default)true


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;

public class InputNoLineWrapRecordsAndCompactCtors {
    class Bar {
        public // violation
        Bar() {
        }
        public void fun() {
        }
    }
    record // violation
    MyRecord1() {
        public MyRecord1() {
        }
        public static void
        doSomething() {
        }
    }
    record MyRecord2() {
        public // violation
        MyRecord2() {
        }
        public void fun() {
        }
    }
    record MyRecord3(String str, int // violation
                     x) {
        public // violation
        MyRecord3{}
    }

    record MyRecord4(String str, int x, // violation
                     int y) {
        public // violation
        MyRecord4{}
    }

    Record recordMethod (int x) {
        record MyMethodRecord(int // violation
                                      x) {
            public MyMethodRecord // violation
            {}
        }

        return new MyMethodRecord(42);
    }
}
