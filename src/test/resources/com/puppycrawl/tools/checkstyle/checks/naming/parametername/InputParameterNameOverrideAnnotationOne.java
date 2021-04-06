package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

/* Config:
 *
 * format = "^h$"
 * ignoreOverridden = false
 */

public class InputParameterNameOverrideAnnotationOne {

    @Override
    public boolean equals(Object o) { // violation
        return super.equals(o);
    }

    @SuppressWarnings("")
    public void foo(Object object) { // violation

    }

    public void foo2(Integer aaaa) {} // violation

    void foo3() {} // No NPE here! // ok

    void foo4(int abc, int bd) {} // violation

    int foo5(int abc) {return 1;} // violation

    private int field;
    private java.util.Set<String> packageNames;

    InputParameterNameOverrideAnnotationOne() {} // No NPE here! // ok

    InputParameterNameOverrideAnnotationOne(int fie, java.util.Set<String> pkgNames) {} // violation


}
