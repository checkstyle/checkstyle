package com.puppycrawl.tools.checkstyle.indentation;

public class InvalidInputThrowsIndent {

    public InvalidInputThrowsIndent()
    {
    }

    // This should pass for our reconfigured throwsIndent test.
    private void myFunc()
            throws Exception
    {
    }

    // This is the out of the box default configuration, but should fail
    // for our reconfigured test.
    private void myFunc2()
        throws Exception
    {
    }
}
