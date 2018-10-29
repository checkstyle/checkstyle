package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;
/** Input file */
public class InputIllegalThrows {

    public void method() throws NullPointerException
    { // no code
    }

    public java.lang.Throwable methodOne() throws RuntimeException
    {
        return null;
    }

    public void methodTwo() throws java.lang.RuntimeException, java.lang.Error
    {
    }

    public void finalize() throws Throwable {

    }
}
