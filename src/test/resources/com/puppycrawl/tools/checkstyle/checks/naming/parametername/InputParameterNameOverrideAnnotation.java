package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

public class InputParameterNameOverrideAnnotation {

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @SuppressWarnings("")
    public void foo(Object object) {

    }

    public void foo2(Integer aaaa) {}

    void foo3() {} // No NPE here!

    void foo4(int abc, int bd) {} // No NPE here!

    int foo5(int abc) {return 1;} // No NPE here!

    private int field;
    private java.util.Set<String> packageNames;

    InputParameterNameOverrideAnnotation() {} // No NPE here!

    InputParameterNameOverrideAnnotation(int fie, java.util.Set<String> pkgNames) {} // No NPE here!


}
