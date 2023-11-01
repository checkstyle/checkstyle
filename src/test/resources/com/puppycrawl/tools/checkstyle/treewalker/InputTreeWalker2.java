package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalker2 {
//    void fn1(int v1) {}
    protected void fn2(int V2) {} // violation "Parameter name 'V2' must match pattern"
//    private void fn3(int a) {}

    public void method() {
        boolean test = true;
        if(test) {
        }
    }
}
