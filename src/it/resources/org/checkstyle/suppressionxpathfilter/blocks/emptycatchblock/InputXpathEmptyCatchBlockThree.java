package org.checkstyle.suppressionxpathfilter.blocks.emptycatchblock;

public class InputXpathEmptyCatchBlockThree {

    public void process() {
        String value = "test";
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {} //warn
    }
}
