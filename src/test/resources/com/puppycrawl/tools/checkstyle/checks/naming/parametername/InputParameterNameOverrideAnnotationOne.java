/*
ParameterName
format = ^h$
ignoreOverridden = (default)false
accessModifiers = (default)public, protected, package, private


*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

public class InputParameterNameOverrideAnnotationOne {

    @Override
    public boolean equals(Object o) { // violation 'Name 'o' must match pattern'
        return super.equals(o);
    }

    @SuppressWarnings("")
    public void foo(Object object) { // violation 'Name 'object' must match pattern'

    }

    public void foo2(Integer data) {} // violation 'Name 'data' must match pattern'

    void foo3() {} // No NPE here!

    void foo4(int abc, int bd) {} // violation 'Name 'abc' must match pattern'
    // violation above 'Name 'bd' must match pattern'
    int foo5(int abc) {return 1;} // violation 'Name 'abc' must match pattern'

    private int field;
    private java.util.Set<String> packageNames;

    InputParameterNameOverrideAnnotationOne() {} // No NPE here!

    InputParameterNameOverrideAnnotationOne(int fie, java.util.Set<String> pkgNames) {}
    // 2 violations above:
    //  'Name 'fie' must match pattern'
    //  'Name 'pkgNames' must match pattern'

}
