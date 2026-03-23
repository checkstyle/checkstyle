package org.checkstyle.suppressionxpathfilter.blocks.googlerightcurly;

public class InputXpathGoogleRightCurlyOne {
    public void test(int x) {
        if (x > 0) {
            return;
        }  //warn
        else if (x < 0) {

        }
    }
}
