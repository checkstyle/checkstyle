package org.checkstyle.suppressionxpathfilter.whitespace.typebodypadding;

public class InputXpathTypeBodyPaddingLocal {

    public void myMethod() {
        class LocalClass { // warn
            int c = 0;

        }
    }

}
