/*
OpenjdkMethodParameterAlignment
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.openjdkmethodparameteralignment;

public class InputOpenjdkMethodParameterAlignmentConstructors {

    InputOpenjdkMethodParameterAlignmentConstructors() {
    }

    InputOpenjdkMethodParameterAlignmentConstructors(int a,
                                                     int b,
                                                     int c) {
    }

    InputOpenjdkMethodParameterAlignmentConstructors(long a,
                                                     long b, long c) {
        // violation above 'Only one parameter is allowed per line in a vertical list.'
    }

    InputOpenjdkMethodParameterAlignmentConstructors(String a, String b,
            String c) {
    }

    class Inner {

        Inner(int a,
              int b, int c) {
            // violation above 'Only one parameter is allowed per line in a vertical list.'
        }
    }

    enum Nested {

        FIRST;

        Nested() {
        }
    }

    record Point(int x, int y) {

        Point(int x,
              int y, boolean unused) {
            // violation above 'Only one parameter is allowed per line in a vertical list.'
            this(x, y);
        }

        Point {
        }
    }
}
