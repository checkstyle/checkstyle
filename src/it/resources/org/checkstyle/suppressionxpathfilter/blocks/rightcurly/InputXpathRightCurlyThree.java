package org.checkstyle.suppressionxpathfilter.blocks.rightcurly;

public class InputXpathRightCurlyThree {
    public void sample(boolean flag) {
        if (flag) { String.CASE_INSENSITIVE_ORDER.equals("it is ok."); } //warn
    }
}
