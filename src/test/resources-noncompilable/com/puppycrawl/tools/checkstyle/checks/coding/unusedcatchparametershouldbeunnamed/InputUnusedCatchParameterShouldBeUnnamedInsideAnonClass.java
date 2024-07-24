/*
UnusedCatchParameterShouldBeUnnamed

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedcatchparametershouldbeunnamed;

public class InputUnusedCatchParameterShouldBeUnnamedInsideAnonClass {

    void testNestedInsideAnonClass() {
        try {
            int x = 1 / 0;
        } catch (Exception e) {  // violation
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        int x = 1 / 0;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            };
        }
    }

    void testNestedInsideAnonClass2() {
        try {
            int x = 1 / 0;
        } catch (Exception e) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        int x = 1 / 0;
                        throw e;
                    }
                    catch (Exception e) {  // violation

                    }
                }
            };
        }
    }

    void testNestedInsideAnonClass3() {
        try {
            int x = 1 / 0;
        } catch (Exception e) { // violation
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        int x = 1 / 0;
                    }
                    catch (Exception e) { // violation

                    }
                }
            };
        }
    }

    void testNestedInsideAnonClass4() {
        try {
            int x = 1 / 0;
        } catch (Exception e) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        int x = 1 / 0;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println(e.getMessage());
                }
            };
        }
    }

    void testNestedInsideAnonClass5() {
        try {
            int x = 1 / 0;
        } catch (final Exception e) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        int x = 1 / 0;
                    } catch (Exception ex) { // violation
                        System.out.println(e.getMessage());
                    }
                }
            };
        }
    }

    void testNestedInsideAnonClass6() {
        try {
            int x = 1 / 0;
        } catch (final Exception e) { // violation
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        int x = 1 / 0;
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            };
        }
    }
}
