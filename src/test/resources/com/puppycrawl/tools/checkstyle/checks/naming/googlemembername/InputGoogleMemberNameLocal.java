/*
GoogleMemberName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemembername;

/** Test that local variables are NOT checked. */
public class InputGoogleMemberNameLocal {

    int validMember;

    void method() {
        int Foo = 1;
        int f = 2;
        int foo_bar = 3;
        int gradle_9_5_1 = 4;
        int _bad = 5;

        for (int i = 0; i < 10; i++) {
            int loop_var = i;
        }

        try {
            int try_var = 1;
        }
        catch (Exception e) {
            int catch_var = 2;
        }
    }
}
