/*
GoogleMemberName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemembername;

/** Test that static final (constants) are skipped. */
public class InputGoogleMemberNameStaticFinal {

    static final int FOO_BAR = 1;
    static final int f = 2;
    static final int _BAD = 3;
    static final int foo_bar = 4;
    static final int gradle_9_5_1 = 5;

    public static final int MAX_VALUE = 100;
    private static final String NAME = "test";
    protected static final int FOO = 1;

}
