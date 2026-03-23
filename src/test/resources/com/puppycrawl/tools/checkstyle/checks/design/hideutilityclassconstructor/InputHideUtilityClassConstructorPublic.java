/*
HideUtilityClassConstructor
ignoreAnnotatedBy = (default)

*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

// violation below
public class InputHideUtilityClassConstructorPublic {
    public InputHideUtilityClassConstructorPublic() {}

    private static int value = 0;
    public static void foo (int val) { value = val;}
}
