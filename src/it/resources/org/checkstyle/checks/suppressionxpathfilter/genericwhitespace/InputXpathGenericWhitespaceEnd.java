package org.checkstyle.checks.suppressionxpathfilter.genericwhitespace;

import java.util.Collections;

public class InputXpathGenericWhitespaceEnd {
    void bad(Class<? > cls) {//warn
    }
    void good(Class<?> cls) {
    }
}
