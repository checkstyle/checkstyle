/*
GenericWhitespace


*/

package org.checkstyle.suppressionxpathfilter.whitespace.genericwhitespace;

import java.util.Collections;

public class InputXpathGenericWhitespaceStartThree {
    < E> void bad() { // violation "'<' is followed by whitespace."
    }
    <E> void good() {
    }
}
