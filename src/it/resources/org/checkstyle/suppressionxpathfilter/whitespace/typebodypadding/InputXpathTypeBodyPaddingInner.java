package org.checkstyle.suppressionxpathfilter.whitespace.typebodypadding;

public class InputXpathTypeBodyPaddingInner {

    int a = 0;

    static class inner { // warn
        int b = 0;
    }
}
