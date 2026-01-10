/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

public class InputOneStatementPerLineEmptyStatement2 {
    String str = "123";
    ;  // ok empty statement on another line

    @Deprecated
    String str = "123";
    ;  // ok empty statement on another line

    public void method() {
        int a = 1;
        ; // ok empty statement on another line

        ; // ok empty statement on another line
        ; // ok empty statement on another line
        ; // ok empty statement on another line
    }
}
