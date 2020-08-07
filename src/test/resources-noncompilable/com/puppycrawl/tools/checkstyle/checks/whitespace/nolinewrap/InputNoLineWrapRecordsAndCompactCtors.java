//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;

/* Config:
 * tokens = {RECORD_DEF, CLASS_DEF, CTOR_DEF, COMPACT_CTOR_DEF}
 *
 */
public class InputNoLineWrapRecordsAndCompactCtors {
    class Bar { // OK
        public // violation, constructor definition not wrapped in a single line
        Bar() {
        }
        public void fun() { // OK
        }
    }
    record // should be a violation, record definition not wrapped in a single line
    MyRecord1() {
        public MyRecord1() { // OK
        }
        public static void // violation, method definition not wrapped in a single line
        doSomething() {
        }
    }
    record MyRecord2() { // OK
        public // violation, constructor definition not wrapped in a single line
        MyRecord2() {
        }
        public void fun() { // OK
        }
    }
    record MyRecord3(String str, int // violation
                     x) {
        public // should be a violation, constructor definition not wrapped in a single line
        MyRecord3{}
    }

    record MyRecord4(String str, int x, // violation
                     int y) {
        public // should be a violation, constructor definition not wrapped in a single line
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
