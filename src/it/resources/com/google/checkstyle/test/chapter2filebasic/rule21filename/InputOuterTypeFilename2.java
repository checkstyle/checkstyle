package com.google.checkstyle.test.chapter2filebasic.rule21filename;

/**
 * Test for illegal tokens
 */
public class InputOuterTypeFilename2 //ok
{
    public void defaultMethod()
    {
        int i = 0;
        switch (i)
        {
            default:
                i--;
                i++;
                break;
        }
    }

    public native void nativeMethod();

    public void methodWithLiterals()
    {
        final String ref = "<a href=\"";
        final String refCase = "<A hReF=\"";
    }
}
