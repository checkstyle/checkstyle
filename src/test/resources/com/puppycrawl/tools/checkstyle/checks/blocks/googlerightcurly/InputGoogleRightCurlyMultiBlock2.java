/*
GoogleRightCurly

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

public class InputGoogleRightCurlyMultiBlock2 {

    void method() {
        int a = 1;
        int c = 1;
        int b = 1;

        if (a == 1) {
            c = a + b;
        } else if (c == 1) {} else {
            b = c + a;
        }
        // violation 3 lines above ''}' at column 29 should have line break before'

        if (b == 1) {
            if (c == 1) {}
        } else {
        a = b + c;}
        // violation above ''}' at column 19 should be alone on a line'

        if (a == b) { c++; }
        // violation above ''}' at column 28 should be alone on a line'

        if (c == 1) {
           c += b;
           b = 1;} else if (a == 1) {} else {}
        // 3 violations above:
        // ''}' at column 18 should have line break before'
        // ''}' at column 38 should have line break before'
        // ''}' at column 46 should be alone on a line'
    }

    void method2() {
        int k = 1;
        boolean flag = true;
        if (k == 1
          && flag) {
           k++;
        }
        else if (!flag) {
            k += 2;
        }
        else {
            flag = false;
        }
        // violation 7 lines above ''}' at column 9 should be on the same line as .*/else'
        // violation 5 lines above ''}' at column 9 should be on the same line as .*/else'

    }
}
