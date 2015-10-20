package com.puppycrawl.tools.checkstyle.checks.design;

public class InputUtilityClassConstructorPrivate {
    private InputUtilityClassConstructorPrivate() {}

    private static int value = 0;
    public static void foo (int val) { value = val;}
}
