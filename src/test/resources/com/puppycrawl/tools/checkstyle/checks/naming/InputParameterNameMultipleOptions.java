package com.puppycrawl.tools.checkstyle.checks.naming;

import java.util.Set;

public class InputParameterNameMultipleOptions {

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @SuppressWarnings("")
    public void foo1(Object object) {

    }

    public void foo2(Integer aaaa) {}

    void foo3() {} // No NPE here!

    void foo4(int abc, int bd) {} // No NPE here!

    int foo5(int abc) {return 1;} // No NPE here!

    private int field;
    private Set<String> packageNames;

    InputParameterNameMultipleOptions() {} // No NPE here!

    InputParameterNameMultipleOptions(int field, Set<String> packageNames) {} // No NPE here!

    void foo6() {
        try {

        }
        catch (Exception ex) {

        }
    }

    void foo7() {
        try {

        }
        catch (NullPointerException | IllegalArgumentException ex) {
            // just to check how the ParentName's option 'skipCahcthParameter' deals with catching
            // multiple exception types and
        }
    }
}
