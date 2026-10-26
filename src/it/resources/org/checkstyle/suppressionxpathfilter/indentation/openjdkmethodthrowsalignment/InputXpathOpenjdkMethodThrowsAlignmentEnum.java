/*
OpenjdkMethodThrowsAlignment


*/

package org.checkstyle.suppressionxpathfilter.indentation.openjdkmethodthrowsalignment;

public enum InputXpathOpenjdkMethodThrowsAlignmentEnum {
    VALUE;

    public void method(int first,
                       int second) throws Exception { }
                       // violation above "The 'throws' clause should be on a new line"
}
