package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

public class InputHideUtilityClassConstructorPublic {
    public InputHideUtilityClassConstructorPublic() {}

    private static int value = 0;
    public static void foo (int val) { value = val;}
}
