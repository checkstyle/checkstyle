package org.checkstyle.suppressionxpathfilter.rightcurly;

public class InputXpathRightCurlyOne {
    public void test(int x) {
        if (x > 0)
        {
            return;
        } //warn
        else if (x < 0) {
            ;
        }
    }
}
