package org.checkstyle.suppressionxpathfilter.whitespace.whitespaceafter;

public class InputXpathWhitespaceAfterIfBlock {
    public void test() {
        if (true) {
            System.out.println("true");
        }

        if(true) { // warn
            System.out.println("true");
        }
    }
}
