package com.puppycrawl.tools.checkstyle.checks.design;

public class UtilityClassConstructorPublic {
    public UtilityClassConstructorPublic() {}

    private static int value = 0;
    public static void foo (int val) { value = val;}
}
