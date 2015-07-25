////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2015
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

public interface InputRedundantStaticModifierInInnerTypeOfInterface {
    static class MyInnerClass { }

    class MyInnerClass2 { }

    static enum MyInnerEnum { }

    enum MyInnerEnum2 { }
}
