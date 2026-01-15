/*
GoogleMemberName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemembername;

/** Test that static non-final members ARE checked. */
public class InputGoogleMemberNameStaticInvalid {

    static int Foo;
    // violation above, 'Member name 'Foo' must start with a lowercase letter, min 2 chars'

    static int f;
    // violation above, 'Member name 'f' must start with a lowercase letter, min 2 chars'

    static int foo_bar;
    // violation above, 'Member name 'foo_bar' has invalid underscore usage'

    static int gradle_9_5_1;
    // violation above, 'Member name 'gradle_9_5_1' has invalid underscore usage'

    private static int _bad;
    // violation above, 'Member name '_bad' has invalid underscore usage'

    public static int CONSTANT_LIKE;
    // violation above, 'Member name 'CONSTANT_LIKE' has invalid underscore usage'
}
