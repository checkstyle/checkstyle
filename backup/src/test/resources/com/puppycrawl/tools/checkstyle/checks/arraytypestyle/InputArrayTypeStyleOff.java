/*
ArrayTypeStyle
javaStyle = false


*/

package com.puppycrawl.tools.checkstyle.checks.arraytypestyle;

public class InputArrayTypeStyleOff
{
    private int[] javaStyle = new int[0]; // violation
    private int cStyle[] = new int[0];
    private int c[] = new int[0];

    public static void mainJava(String[] aJavaStyle) // violation
    {
    }

    public static void mainC(String aCStyle[])
    {
        final int[] blah = new int[0]; // violation
        final boolean isOK1 = aCStyle instanceof String[];
        final boolean isOK2 = aCStyle instanceof java.lang.String[];
        final boolean isOK3 = blah instanceof int[];
    }

    public class Test
    {
        public Test[] // violation
            variable;

        public Test[]
            getTests()
        {
            return null;
        }

        public Test[] getNewTest()
        {
            return null;
        }

        public Test getOldTest()[] // violation
        {
            return null;
        }

        public Test getOldTests()[][] // 2 violations
        {
            return null;
        }

        public Test[]
            getMoreTests()[][] // 2 violations
        {
            return null;
        }

        public Test[][] getTests2()
        {
            return null;
        }
    }
    public static void foo(java.util.Collection < ? extends InputArrayTypeStyle[] > collection) {}
}
