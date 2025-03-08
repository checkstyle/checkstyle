/*
MethodLength
max = 2
countEmpty = (default)true
tokens = (default)METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;

public class InputMethodLengthRecordsAndCompactCtors {

    record MyTestRecord() {
        static {
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");
        }
    }

    //compact ctor
    record MyTestRecord2() {
        // violation below 'Method MyTestRecord2 length is 6 lines (max allowed is 2)'
        public MyTestRecord2 {
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");

        }
    }

    record MyTestRecord3(String str) {
        void foo() { // violation 'Method foo length is 5 lines (max allowed is 2)'
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");
        }
    }

    record MyTestRecord4(int x, int y) {
        // violation below 'Method MyTestRecord4 length is 7 lines (max allowed is 2)'
        public MyTestRecord4() {
            this(4, 5);
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");

        }
    }

    record MyTestRecord5() {
        static {
            int y = 5;
            int z = 10;
            String newString = String.valueOf(y);
            System.out.println(newString);
            System.out.println("Value of y: " + newString);
            System.out.println(y + z);
        }
    }

    class LocalRecordHelper {
        Class<?> m(int x) { // violation 'Method m length is 15 lines (max allowed is 2)'
            record R76(int x) {

                public R76 { // violation 'Method R76 length is 8 lines (max allowed is 2)'
                    int y = 5;
                    int z = 10;
                    String newString = String.valueOf(y);
                    System.out.println(newString);
                    System.out.println("Value of y: " + newString);
                    System.out.println(y + z);
                }

            }
            return R76.class;
        }
    }
}
