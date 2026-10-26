/*
GenericWhitespace


*/

package org.checkstyle.suppressionxpathfilter.whitespace.genericwhitespace;

import java.util.Collections;

public class InputXpathGenericWhitespaceEnd {
    void bad(Class<? > cls) { // violation "'>' is preceded with whitespace."
    }
    void good(Class<?> cls) {
    }
}
