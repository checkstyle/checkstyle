/*
ParameterName
format = ^h$
ignoreOverridden = true
accessModifiers = (default)public, protected, package, private


*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

public class InputParameterNameOverrideAnnotation {

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @SuppressWarnings("")
    public void foo(Object object) { // violation

    }

    public void foo2(Integer aaaa) {} // violation

    void foo3() {} // No NPE here! // ok

    void foo4(int abc, int bd) {} // 2 violations

    int foo5(int abc) {return 1;} // violation

    private int field;
    private java.util.Set<String> packageNames;

    InputParameterNameOverrideAnnotation() {} // No NPE here! // ok

    InputParameterNameOverrideAnnotation(int fie, java.util.Set<String> pkgNames) {} // 2 violations


}
