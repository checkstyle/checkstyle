package org.checkstyle.suppressionxpathfilter.typename;

public class InputXpathTypeNameDefault {
    public interface FirstName {} // OK
    private class SecondName_ {} // warn

}
