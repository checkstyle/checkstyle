/*
HideUtilityClassConstructor
ignoreAnnotatedBy = (default)

*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

public class InputHideUtilityClassConstructorPublic { // violation
    public InputHideUtilityClassConstructorPublic() {}

    private static int value = 0;
    public static void foo (int val) { value = val;}
}
