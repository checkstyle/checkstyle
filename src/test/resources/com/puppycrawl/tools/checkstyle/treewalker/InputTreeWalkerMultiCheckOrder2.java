/*
com.puppycrawl.tools.checkstyle.TreeWalkerTest$ViolationTreeWalkerMultiCheckOrder2

*/
package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalkerMultiCheckOrder2 {
    void fn1(int v1) {}
    protected void fn2(int V2) {} // violation "Parameter name 'V2' must match pattern"
    private void fn3(int a) {}
    public void fn4(int b) {} // violation "Parameter name 'b' must match pattern"
}
