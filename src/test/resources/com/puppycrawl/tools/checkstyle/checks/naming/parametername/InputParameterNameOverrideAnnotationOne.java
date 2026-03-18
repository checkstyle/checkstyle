/*
ParameterName
format = ^h$
ignoreOverridden = (default)false
accessModifiers = (default)public, protected, package, private


*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

public class InputParameterNameOverrideAnnotationOne {

    @Override
    public boolean equals(Object o) {
    // violation above, ''o'' must match pattern ''^h$''
        return super.equals(o);
    }

    @SuppressWarnings("")
    public void foo(Object object) {
    // violation above, ''object'' must match pattern ''^h$''

    }

    public void foo2(Integer data) {}
    // violation above, ''data'' must match pattern ''^h$''

    void foo3() {} // No NPE here!

    void foo4(int abc, int bd) {}
    // 2 violations above:
    //    ''abc'' must match pattern ''^h$''
    //    ''bd'' must match pattern ''^h$''

    int foo5(int abc) {return 1;}
    // violation above, ''abc'' must match pattern ''^h$''

    private int field;
    private java.util.Set<String> packageNames;

    InputParameterNameOverrideAnnotationOne() {} // No NPE here!

    InputParameterNameOverrideAnnotationOne(int fie, java.util.Set<String> pkgNames) {}
    // 2 violations above:
    //    ''fie'' must match pattern ''^h$''
    //    ''pkgNames'' must match pattern ''^h$''


}
