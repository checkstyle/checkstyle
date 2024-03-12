package org.checkstyle.suppressionxpathfilter.emptycatchblock;

public class InputXpathEmptyCatchBlock2 {
    public static void main(String[] args) {

        try {
            throw new RuntimeException();
        } catch (RuntimeException e) /*warn*/ {
        }
    }
}
