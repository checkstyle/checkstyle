package org.checkstyle.suppressionxpathfilter.emptycatchblock;

public class InputXpathEmptyCatchBlock1 {
    public static void main(String[] args) {

        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {} //warn
    }
}
