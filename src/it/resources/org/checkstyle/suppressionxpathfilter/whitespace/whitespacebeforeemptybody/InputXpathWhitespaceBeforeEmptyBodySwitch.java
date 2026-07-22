package org.checkstyle.suppressionxpathfilter.whitespace.whitespacebeforeemptybody;

public class InputXpathWhitespaceBeforeEmptyBodySwitch {

    void test(int x) {
        switch (x){}; // warn

        switch (x) { }
    }
}
