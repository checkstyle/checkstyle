/*
com.puppycrawl.tools.checkstyle.treewalker.InputTreeWalker2$ViolationTreeWalkerMultiCheckOrder

*/
package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalkerMultiCheckOrder {
    public void method() {
        boolean test = true;
        if(test) {
           // violation above, ''if' is not followed by whitespace.'
        }
    }
}
