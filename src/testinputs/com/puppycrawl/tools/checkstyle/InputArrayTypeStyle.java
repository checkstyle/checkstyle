////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

/**
 * Test case for ArrayTypeStyle (Java vs C)
 * @author lkuehne
 **/
public class InputArrayTypeStyle
{
    private int[] javaStyle = new int[0];
    private int cStyle[] = new int[0];

    public static void mainJava(String[] aJavaStyle)
    {
    }

    public static void mainC(String aCStyle[])
    {
    }
}
