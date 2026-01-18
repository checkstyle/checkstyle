/*
GoogleNonConstantFieldName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlenonconstantfieldname;

/** Test that static non-final members are not checked. */
public class InputGoogleNonConstantFieldNameStaticSkipped {

    static int Foo;
    static int f;
    static int foo_bar;
    static int gradle_9_5_1;
    private static int _bad;
    public static int CONSTANT_LIKE;
}
