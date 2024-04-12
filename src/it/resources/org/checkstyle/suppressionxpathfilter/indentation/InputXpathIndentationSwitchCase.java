package org.checkstyle.suppressionxpathfilter.indentation;

public class InputXpathIndentationSwitchCase {
    void test() {
        int key = 5;
        switch (key) {
        case 1: // warn
                break;
        }
    }
}
