package org.checkstyle.suppressionxpathfilter.whitespace.typebodypadding;

public class InputXpathTypeBodyPaddingLocal {

    int a = 0;

    public void myMethod() {
        class LocalClass { // warn
            int c = 0;
        }
    }
}
