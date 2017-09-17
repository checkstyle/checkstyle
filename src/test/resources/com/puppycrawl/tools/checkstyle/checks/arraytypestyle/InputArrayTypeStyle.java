////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.arraytypestyle;

/**
 * Test case for ArrayTypeStyle (Java vs C)
 * @author lkuehne
 **/
public class InputArrayTypeStyle
{
    private int[] javaStyle = new int[0];
    private int cStyle[] = new int[0];
    private int c[] = new int[0];

    public static void mainJava(String[] aJavaStyle)
    {
    }

    public static void mainC(String aCStyle[])
    {
        final int[] blah = new int[0];
        final boolean isOK1 = aCStyle instanceof String[];
        final boolean isOK2 = aCStyle instanceof java.lang.String[];
        final boolean isOK3 = blah instanceof int[];
    }

    public class Test
    {
        public Test[]
            variable;

        public Test[]
            getTests()
        { // we shouldn't check methods because there is no alternatives.
            return null;
        }

        public Test[] getNewTest()
        {
            return null;
        }
    }
}
