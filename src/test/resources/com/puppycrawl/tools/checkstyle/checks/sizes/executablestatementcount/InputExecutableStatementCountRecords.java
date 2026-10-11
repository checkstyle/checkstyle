/*
ExecutableStatementCount
max = 1
tokens = (default)CTOR_DEF,METHOD_DEF,INSTANCE_INIT,STATIC_INIT,SLIST,COMPACT_CTOR_DEF,LAMBDA


*/


package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

public class InputExecutableStatementCountRecords {

    record MyTestRecord() {
        // violation below 'Executable statement count is 3'
        static {
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");
        }
    }

    //compact ctor
    record MyTestRecord2() {
        // violation below 'Executable statement count is 3'
        public MyTestRecord2 {
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");

        }
    }

    record MyTestRecord3(String str) {
        // violation below 'Executable statement count is 3'
        void foo() {
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");
        }
    }

    record MyTestRecord4(int x, int y) {
        // violation below 'Executable statement count is 4'
        public MyTestRecord4() {
            this(4, 5);
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");

        }
    }

    record MyTestRecord5() {
        // violation below 'Executable statement count is 6'
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
        Class<?> m(int x) {
            record R76(int x) {

                // violation below 'Executable statement count is 6'
                public R76 {
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
