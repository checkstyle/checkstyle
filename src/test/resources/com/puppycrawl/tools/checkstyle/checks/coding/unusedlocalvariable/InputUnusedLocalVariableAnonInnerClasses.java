/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableAnonInnerClasses {

    static int a = 12, b = 13, c = 14;

    public void testAnonymousInnerClass() {
        int a = 12; // violation
        int b = 12; // violation
        int k = 14;
        Test obj = new Test() { // violation
            int a = 2;

            private void testSameName(int s) {
                s = a + InputUnusedLocalVariable.a;
                Test obj = new Test() { // violation
                    int b = 1;

                    private void testSameNameNested(int s) {
                        s = b + InputUnusedLocalVariable.b + InputUnusedLocalVariable.a + a + k;
                    }
                };
            }
        };

        Test obj2; // violation
        obj2 = new Test() {
            int a = 1;
            int b = 1;
            int c = 0;

            private void testSameName(int s) {
                s = a + b + this.a + this.b + InputUnusedLocalVariableAnonInnerClasses.a
                        + InputUnusedLocalVariableAnonInnerClasses.b;
            }
        };
    }

    {
        int m = 12; // violation
        int l = 2; // violation
        d obj = new d() {
            void method() {
                m += l;
            }
        };
        obj.getClass();
    }

    static {
        int a = 1;
        int x = 2;
        int h = 3; // violation
        m.Test obj = new m().new Test() {
            void method() {
                boolean v = // violation
                        a == x == (h == 2);
            }
        };
        obj.getClass();
    }

    class Test {
        private int k = 1;
    }

    static class m {
        public int x = 1;

        class Test {
            public int h = 12;
        }
    }

    class d {
        protected int m = 11;
        int l = 14;
    }

}
