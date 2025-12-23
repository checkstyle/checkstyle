/*
GoogleMemberName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemembername;

/** Test that correctly distinguishes static/final combinations. */
public class InputGoogleMemberNameMixedModifiers {

    static final int FOO_BAR = 1;
    static final int _bad = 2;

    int Static_Bad;
    // violation above, 'Member name 'Static_Bad' has invalid underscore usage'

    int Instance_Bad;
    // violation above, 'Member name 'Instance_Bad' has invalid underscore usage'

    final int Final_Instance=1;
    // violation above, 'Member name 'Final_Instance' has invalid underscore usage'

}
