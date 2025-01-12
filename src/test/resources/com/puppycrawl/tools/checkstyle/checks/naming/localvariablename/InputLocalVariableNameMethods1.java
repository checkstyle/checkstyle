package com.puppycrawl.tools.checkstyle.checks.naming.localvariablename;

public class InputLocalVariableNameMethods1 {
    //
    // Parameter name format tests
    //

    /**
     * @return hack
     * @param badFormat1 bad format
     * @param badFormat2 bad format
     * @param badFormat3 bad format
     * @throws java.lang.Exception abc
     **/
    int test1(int badFormat1,int badFormat2,
              final int badFormat3)
        throws java.lang.Exception
    {
        return 0;
    }

    /** method that is 20 lines long **/
    private void longMethod()
    {
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
    }
}
