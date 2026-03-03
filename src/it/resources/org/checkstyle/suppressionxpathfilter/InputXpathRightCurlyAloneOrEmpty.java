package org.checkstyle.suppressionxpathfilter;
public class InputXpathRightCurlyAloneOrEmpty {
    public @interface TestAnnotation {
        int val(); } // violation
    void method() {
        int x = 1; } // violation
}
