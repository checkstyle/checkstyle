/*
GoogleMemberName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemembername;

/** Test that static non-final members ARE checked. */
public class InputGoogleMemberNameStaticSkipped {

    static int Foo;
    static int f;
    static int foo_bar;
    static int gradle_9_5_1;
    private static int _bad;
    public static int CONSTANT_LIKE;
}
