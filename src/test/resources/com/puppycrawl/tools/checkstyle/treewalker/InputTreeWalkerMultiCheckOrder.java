/*
com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck


com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterCheck

*/
package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalkerMultiCheckOrder {
    public void method() {
        boolean test = true;
        if(test) {  // 2 violations

        }
    }
}
