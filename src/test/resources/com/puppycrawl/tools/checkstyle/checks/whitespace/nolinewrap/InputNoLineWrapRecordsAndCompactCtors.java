/*
NoLineWrap
tokens = RECORD_DEF, CLASS_DEF, CTOR_DEF, COMPACT_CTOR_DEF


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;

public class InputNoLineWrapRecordsAndCompactCtors {
    class Bar {
        public // violation 'CTOR_DEF statement should not be line-wrapped.'
        Bar() {
        }
        public void fun() {
        }
    }
    record // violation 'RECORD_DEF statement should not be line-wrapped.'
    MyRecord1() {
        public MyRecord1() {
        }
        public static void
        doSomething() {
        }
    }
    record MyRecord2() {
        public // violation 'CTOR_DEF statement should not be line-wrapped.'
        MyRecord2() {
        }
        public void fun() {
        }
    }
    record MyRecord3(String str, int // violation 'RECORD_DEF statement should not be line-wrapped.'
                     x) {
        public // violation 'COMPACT_CTOR_DEF statement should not be line-wrapped.'
        MyRecord3{}
    }

    // violation below 'RECORD_DEF statement should not be line-wrapped.'
    record MyRecord4(String str, int x,
                     int y) {
        public // violation 'COMPACT_CTOR_DEF statement should not be line-wrapped.'
        MyRecord4{}
    }

    Record recordMethod (int x) {
        record MyMethodRecord(int // violation 'RECORD_DEF statement should not be line-wrapped.'
                                      x) {
            public MyMethodRecord
            // violation above 'COMPACT_CTOR_DEF statement should not be line-wrapped.'
            {}
        }

        return new MyMethodRecord(42);
    }
}
