/*
ParameterName
format = ^h$
ignoreOverridden = (default)false
accessModifiers = (default)public, protected, package, private


*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

public class InputParameterNameOverrideAnnotationOne {

    @Override
    public boolean equals(Object o) { // violation
        return super.equals(o);
    }

    @SuppressWarnings("")
    public void foo(Object object) { // violation

    }

    public void foo2(Integer aaaa) {} // violation

    void foo3() {} // No NPE here!

    void foo4(int abc, int bd) {} // 2 violations

    int foo5(int abc) {return 1;} // violation

    private int field;
    private java.util.Set<String> packageNames;

    InputParameterNameOverrideAnnotationOne() {} // No NPE here!
    // 2 violations below
    InputParameterNameOverrideAnnotationOne(int fie, java.util.Set<String> pkgNames) {}


}
