package org.checkstyle.suppressionxpathfilter.illegalthrows;

public class InputXpathIllegalThrowsTwo {
    public void methodOne() throws NullPointerException
    {
    }

    public void methodTwo() throws java.lang.Error //warn
    {
    }
}
