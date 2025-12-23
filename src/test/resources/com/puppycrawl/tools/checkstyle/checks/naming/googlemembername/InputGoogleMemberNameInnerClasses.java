/*
GoogleMemberName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemembername;

/** Test inner class members are checked. */
public class InputGoogleMemberNameInnerClasses {

    class InnerClass {

        int Inner_Bad;
        // violation above, 'Member name 'Inner_Bad' has invalid underscore usage'

        int innerValid;

        class NestedClass {

            int Nested_Bad;
            // violation above, 'Member name 'Nested_Bad' has invalid underscore usage'

            int nestedValid;
        }
    }

    static class StaticInner {
        int staticInnerField;
        int Static_Bad;
        // violation above, 'Member name 'Static_Bad' has invalid underscore usage'
    }
}
