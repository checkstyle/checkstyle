/*
GoogleRightCurly


*/

package org.checkstyle.suppressionxpathfilter.blocks.googlerightcurly;

public class InputXpathGoogleRightCurlyThree {
    public void test(int x) {
        if (x > 0) {
            return;
        } else if (x < 0) {} else { // violation ''}' at column 28 should have line break before.'
            int a = 1;
        }
    }
}
