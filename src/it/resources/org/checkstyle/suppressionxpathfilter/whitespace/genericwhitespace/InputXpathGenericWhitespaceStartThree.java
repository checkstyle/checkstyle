package org.checkstyle.suppressionxpathfilter.whitespace.genericwhitespace;

import java.util.Collections;

public class InputXpathGenericWhitespaceStartThree {
    < E> void bad() {//warn
    }
    <E> void good() {
    }
}
