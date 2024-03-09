package org.checkstyle.suppressionxpathfilter.typename;

public class InputXpathTypeName1 {
    public interface FirstName {} // OK
    private class SecondName_ {} // warn

}
