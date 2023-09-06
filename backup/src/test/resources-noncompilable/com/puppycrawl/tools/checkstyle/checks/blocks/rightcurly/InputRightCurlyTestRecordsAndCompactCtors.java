/*
RightCurly
option = (default)SAME
tokens = RECORD_DEF, COMPACT_CTOR_DEF, CTOR_DEF, METHOD_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import org.w3c.dom.Node;

public class InputRightCurlyTestRecordsAndCompactCtors {

    record MyTestRecord(String string, Record rec) {
        private boolean inRecord(Object obj) {
            int value = 0;
            if (obj instanceof Integer i) {
                value = i;
            }
            return value > 10;
        } } // 2 violations

    record MyTestRecord2() {
        MyTestRecord2(String one, String two, String three) {
            this(); } // violation ''}' at column 21 should have line break before'
    }

    record MyTestRecord3(Integer i, Node node) {
        public MyTestRecord3{
            int x = 5;} // violation ''}' at column 23 should have line break before'
        public static void main(String... args) {
            System.out.print("ok"); } // violation ''}' at column 37 should have line break before'
    }

    record MyTestRecord4() {} // ok, same line

    record MyTestRecord5() {
        static MyTestRecord mtr =
                new MyTestRecord("my string", new MyTestRecord4());}
                // violation above ''}' at column 68 should be alone on a line'
    class MyTestClass {
        private MyTestRecord mtr =
                new MyTestRecord("my string", new MyTestRecord4());} // ok
}
