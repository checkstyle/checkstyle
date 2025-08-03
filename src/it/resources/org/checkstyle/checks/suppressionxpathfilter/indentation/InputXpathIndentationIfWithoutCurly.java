package org.checkstyle.checks.suppressionxpathfilter.indentation;

public class InputXpathIndentationIfWithoutCurly {
    void test() {
        if (true)
                e();
        if (true)
        e(); // warn
    }
    void e() {};
}

