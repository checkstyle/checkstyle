/*
NestedIfDepth
max = (default)1


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nestedifdepth;

public class InputNestedIfDepthDefault
{
    void foo() {
        // nesting == 0
        if (true) {
        }

        // nesting == 1
        if (true) {
            if (true) {
            }
        }

        // nesting == 2
        if (true) {
            if (true) {
                if (true) { // violation 'Nested if-else depth is 2 (max allowed is 1)'
                }
            }
        }
    }

    void fooWithElse() {
        // nesting == 0
        if (true) {
        } else {
        }

        // nesting == 1
        if (true) {
            if (true) {
            } else {
            }
        } else {
            if (false) {
            } else {
            }
        }

        // nesting == 2
        if (true) {
            if (true) {
                if (true) { // violation 'Nested if-else depth is 2 (max allowed is 1)'
                } else {
                }
            } else {
                if (false) {
                } else {
                }
            }
        } else {
            if (true) {
                if (true) {
                } else {
                }
            } else {
                if (false) {
                } else {
                }
            }
        }
    }

    void method() {
        // nesting == 2
        if (true) {
            if (true) {
                if (true) { // violation 'Nested if-else depth is 2 (max allowed is 1)'
                } else {
                }
            } else
                if (false) {
                }
                else System.out.println();
        } else
            if (true) {
                if (true) {
                } else System.out.println();
            } else
                if (false) {
                } else System.out.println();
    }

     public void test6() {
         byte tmpByte[];

         if (true) {
                 if (true) {
                     tmpByte = new byte[0];
                 }
         } else {
             if (true) {
                 if (true) { // violation 'Nested if-else depth is 2 (max allowed is 1)'
                     tmpByte = new byte[1];
                 }
             }
             if (false) {
                 if (true) { // violation 'Nested if-else depth is 2 (max allowed is 1)'
                     tmpByte = new byte[2];
                 }
             }
         }
     }
}
