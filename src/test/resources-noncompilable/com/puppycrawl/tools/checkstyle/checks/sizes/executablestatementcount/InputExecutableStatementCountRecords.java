/*
ExecutableStatementCount
max = 1
tokens = (default)CTOR_DEF, METHOD_DEF, INSTANCE_INIT, STATIC_INIT, COMPACT_CTOR_DEF, LAMBDA


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

public class InputExecutableStatementCountRecords {

    record MyTestRecord() {
        static { // violation
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
        static { // violation
            int y = 5;
            int z = 10;
            String newString = String.valueOf(y);
            System.out.println(newString);
            System.out.println("Value of y: " + newString);
            System.out.println(y + z);
        }
    }

    class LocalRecordHelper {
        Class<?> m(int x) {
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

    private int id(int i) {
        return i;
    }

    private final int value = 2;

    private final int field = id(switch(value) {
        case 0 -> -1;
        case 2 -> {
            int temp = 0;
            temp += 3;
            yield temp;
        }
        default -> throw new IllegalStateException();
    });
}
