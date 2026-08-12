package org.checkstyle.suppressionxpathfilter.blocks.googlerightcurly;

public class InputXpathGoogleRightCurlyTwo {
    public void test(int x) {
        if (x > 0) {
            return;
        } else if (x < 0) {
            int a = 0; }  //warn
    }
}
