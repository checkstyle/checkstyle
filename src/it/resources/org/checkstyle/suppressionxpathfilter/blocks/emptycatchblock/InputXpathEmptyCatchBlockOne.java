package org.checkstyle.suppressionxpathfilter.blocks.emptycatchblock;

public class InputXpathEmptyCatchBlockOne {
    public static void main(String[] args) {

        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {} //warn
    }
}
