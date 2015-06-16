package com.puppycrawl.tools.checkstyle.design;

public class UtilityClassConstructorPrivate {
    private UtilityClassConstructorPrivate() {}

    private static int value = 0;
    public static void foo (int val) { value = val;}
}
