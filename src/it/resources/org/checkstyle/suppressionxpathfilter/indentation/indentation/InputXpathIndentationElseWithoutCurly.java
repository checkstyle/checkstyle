package org.checkstyle.suppressionxpathfilter.indentation.indentation;

public class InputXpathIndentationElseWithoutCurly {
    void test() {
        if (true)
            exp();
        else exp();

        if (true)
            exp();
        else
        exp(); // warn
    }
    void exp() {}
}
