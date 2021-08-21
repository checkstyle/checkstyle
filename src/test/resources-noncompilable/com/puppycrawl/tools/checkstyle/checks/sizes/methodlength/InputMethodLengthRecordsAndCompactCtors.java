/*
MethodLength
max = 2
countEmpty = (default)true
tokens = (default)METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;

public class InputMethodLengthRecordsAndCompactCtors {

    record MyTestRecord() {
        static { // ok
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");
        }
    }

    //compact ctor
    record MyTestRecord2() {
        public MyTestRecord2 { // violation
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");

        }
    }

    record MyTestRecord3(String str) {
        void foo() { // violation
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");
        }
    }

    record MyTestRecord4(int x, int y) {
        public MyTestRecord4() { // violation
            this(4, 5);
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");

        }
    }

    record MyTestRecord5() {
        static { // ok
            int y = 5;
            int z = 10;
            String newString = String.valueOf(y);
            System.out.println(newString);
            System.out.println("Value of y: " + newString);
            System.out.println(y + z);
        }
    }

    class LocalRecordHelper {
        Class<?> m(int x) { // violation
            record R76(int x) {

                public R76 { // violation
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
