/*
WhitespaceBeforeEmptyBody


*/

package org.checkstyle.suppressionxpathfilter.whitespace.whitespacebeforeemptybody;

public class InputXpathWhitespaceBeforeEmptyBodySwitch {

    void test(int x) {
        switch (x){}; // violation "'{' is not preceded with whitespace."

        switch (x) { }
    }
}
