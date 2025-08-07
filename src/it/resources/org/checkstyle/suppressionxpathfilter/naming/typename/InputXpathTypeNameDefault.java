package org.checkstyle.suppressionxpathfilter.naming.typename;

public class InputXpathTypeNameDefault {
    public interface FirstName {} // OK
    private class SecondName_ {} // warn

}
