package org.checkstyle.checks.suppressionxpathfilter.typename;

public class InputXpathTypeNameDefault {
    public interface FirstName {} // OK
    private class SecondName_ {} // warn

}
