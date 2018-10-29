////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2015
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public interface InputRedundantModifierStaticInInnerTypeOfInterface {
    static class MyInnerClass { } // violation

    class MyInnerClass2 { }

    static enum MyInnerEnum { } // violation

    enum MyInnerEnum2 { }
}
