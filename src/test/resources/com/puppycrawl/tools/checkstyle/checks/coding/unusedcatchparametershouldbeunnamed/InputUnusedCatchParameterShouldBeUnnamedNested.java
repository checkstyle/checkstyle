/*
UnusedCatchParameterShouldBeUnnamed

*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedcatchparametershouldbeunnamed;

public class InputUnusedCatchParameterShouldBeUnnamedNested {


    void testNestedCatch1() {
        try {
            int x = 1 / 0;
        } catch (Exception e) { // violation 'Unused catch parameter 'e' should be unnamed.'
            try {
                int y = 1 / 0;
            } catch (Exception ex) {  // violation 'Unused catch parameter 'ex' should be unnamed.'
                System.out.println("infinity");
            }
            System.out.println("infinity");
        }
    }

    void testNestedCatch2() {
        try {
            int x = 1 / 0;
        } catch (Exception e) {
            try {
                int y = 1 / 0;
            } catch (Exception ex) {  // violation 'Unused catch parameter 'ex' should be unnamed.'
                e.printStackTrace();
                System.out.println("infinity");
            }
            System.out.println("infinity");
        }
    }

    void testNestedCatch3() {
        try {
            int x = 1 / 0;
        } catch (Exception e) { // violation 'Unused catch parameter 'e' should be unnamed.'
            try {
                int y = 1 / 0;
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("infinity");
            }
            System.out.println("infinity");
        }
    }

    void testNestedCatch4() {
        try {
            int x = 1 / 0;
        } catch (Exception e) {
            try {
                int y = 1 / 0;
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("infinity");
            }
            System.out.println("infinity");
        }
    }

    void testNestedCatch5() {
        try {
            int x = 1 / 0;
        } catch (Exception _) {
            try {
                int y = 1 / 0;
            } catch (Exception _) {
                System.out.println("infinity");
            }
            System.out.println("infinity");
        }
    }

    void testNestedCatchTwoLevels() {
        try {
            int x = 1 / 0;
        } catch (Exception e) {
            try {
                int y = 1 / 0;
            } catch (Exception ex) { // violation 'Unused catch parameter 'ex' should be unnamed.'
                try {
                    int z = 1 / 0;
                } catch (Exception exx) {
                    exx.printStackTrace();
                    System.out.println("infinity");
                }
                e.printStackTrace();
                System.out.println("infinity");
            }
            System.out.println("infinity");
        }
    }
}
