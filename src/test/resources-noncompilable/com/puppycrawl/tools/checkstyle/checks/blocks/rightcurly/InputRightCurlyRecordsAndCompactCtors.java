//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import org.w3c.dom.Node;

/* Config:
 * option = same
 * tokens = {RECORD_DEF, COMPACT_CTOR_DEF, CTOR_DEF, METHOD_DEF}
 *
 */
public class InputRightCurlyRecordsAndCompactCtors {

    record MyTestRecord(String string, Record rec) {
        private boolean inRecord(Object obj) {
            int value = 0;
            if (obj instanceof Integer i) {
                value = i;
            }
            return value > 10;
        } } // violation x2

    record MyTestRecord2() {
        MyTestRecord2(String one, String two, String three) {
            this(); } // violation
    }

    record MyTestRecord3(Integer i, Node node) {
        public MyTestRecord3{
            int x = 5;} // violation
        public static void main(String... args) {
            System.out.println("works!"); } // violation
    }

    record MyTestRecord4() {} // ok, same line

    record MyTestRecord5() {
        static MyTestRecord mtr =
                new MyTestRecord("my string", new MyTestRecord4());} // violation

    class MyTestClass {
        private MyTestRecord mtr =
                new MyTestRecord("my string", new MyTestRecord4());} // ok
}
