/*
HideUtilityClassConstructor


*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

public class InputHideUtilityClassConstructor { // ok

    private static int field;

    protected InputHideUtilityClassConstructor() {
        //does nothing
    }

    public static void method() {}
}
