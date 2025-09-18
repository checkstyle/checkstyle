package org.checkstyle.suppressionxpathfilter.whitespace.typebodypadding;

public class InputXpathTypeBodyPaddingStarting {
    int a = 0; // warn

    static class inner {
        int a = 0;
    }
}
