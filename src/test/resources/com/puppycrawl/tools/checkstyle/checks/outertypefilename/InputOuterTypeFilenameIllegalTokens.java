package com.puppycrawl.tools.checkstyle.checks.outertypefilename;

public class InputOuterTypeFilenameIllegalTokens
{
    public void methodWithPreviouslyIllegalTokens()
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
}
