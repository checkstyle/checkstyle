package org.checkstyle.suppressionxpathfilter.blocks.needbraces;

public class InputXpathNeedBracesEmptyLoopBody {
    private int incrementValue() {
        return 1;
    }

    public void test() {
        while(incrementValue() < 5);; // warn
    }
}
