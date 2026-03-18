package org.checkstyle.suppressionxpathfilter.whitespace.typebodypadding;

public class InputXpathTypeBodyPaddingInner {

    int a = 0;

    static class Inner { // warn
        int b = 0;

    }
}
