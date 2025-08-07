package org.checkstyle.suppressionxpathfilter.coding.illegalthrows;

public class InputXpathIllegalThrowsError {
    public void methodOne() throws NullPointerException
    {
    }

    public void methodTwo() throws java.lang.Error //warn
    {
    }
}
