/*
com.puppycrawl.tools.checkstyle.checks.coding.MultipleVariableDeclarationsCheck


*/

package com.puppycrawl.tools.checkstyle.utils.checkutil;

public class InputCheckUtil2 {
    void method4() {
    int c = // violation 'Only one variable definition per line allowed.'
            12; int a = 1;

    int cb = 12; java. // violation 'Only one variable definition per line allowed.'
            lang.String
            asd = "123";
    }
}
