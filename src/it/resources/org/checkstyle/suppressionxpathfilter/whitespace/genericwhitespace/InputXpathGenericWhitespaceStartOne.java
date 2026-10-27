/*
GenericWhitespace


*/

package org.checkstyle.suppressionxpathfilter.whitespace.genericwhitespace;

import java.util.Collections;

public class InputXpathGenericWhitespaceStartOne {
    public<E> void bad() { // violation "'<' is not preceded with whitespace."
    }
    public <E> void good() {
    }
}
