package com.puppycrawl.tools.checkstyle.design;

public class UtilityClassConstructor {
    private static int value = 0;
    public static void foo (int val) { value = val;}

    public static class Inner {
        public int foo;
    }

    public static class Inner2 {
        public static int foo;
    }
}
