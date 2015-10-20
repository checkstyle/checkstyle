package com.puppycrawl.tools.checkstyle.checks.design;

public class InputUtilityClassConstructorPublic {
    public InputUtilityClassConstructorPublic() {}

    private static int value = 0;
    public static void foo (int val) { value = val;}
}
