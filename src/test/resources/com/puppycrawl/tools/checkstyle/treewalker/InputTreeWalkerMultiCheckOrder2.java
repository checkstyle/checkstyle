/*
com.puppycrawl.tools.checkstyle.treewalker.InputTreeWalker2$ViolationTreeWalkerMultiCheckOrder2

*/
package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalkerMultiCheckOrder2 {
    void fn1(int v1) {}
    protected void fn2(int V2) {}
    // violation above
    // because Name V2 must match pattern ^[a-z]([a-z0-9][a-zA-Z0-9]*)?$
    private void fn3(int a) {}
    public void fn4(int b) {}
    // violation above
    // because Name b must match pattern ^[a-z][a-z0-9][a-zA-Z0-9]*$
}
