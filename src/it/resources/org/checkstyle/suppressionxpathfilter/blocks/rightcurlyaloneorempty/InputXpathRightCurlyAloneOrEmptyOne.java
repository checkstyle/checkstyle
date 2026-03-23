package org.checkstyle.suppressionxpathfilter.blocks.rightcurlyaloneorempty;

public class InputXpathRightCurlyAloneOrEmptyOne {
    public void method() {
        int a = 1;
        if (a == 1) {
        } else { // warn
        }
    }
}
