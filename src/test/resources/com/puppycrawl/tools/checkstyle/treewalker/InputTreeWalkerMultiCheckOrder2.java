/*
com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck
format = ^[a-z]([a-z0-9][a-zA-Z0-9]*)?$
accessModifiers =  protected, package, private

com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck
format = ^[a-z][a-z0-9][a-zA-Z0-9]*$
accessModifiers = public

*/
package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalkerMultiCheckOrder2 {
    void fn1(int v1) {}
    protected void fn2(int V2) {} // violation  "Name 'V2' must match pattern"
    private void fn3(int a) {}
    public void fn4(int b) {} // violation  "Name 'b' must match pattern"
}
