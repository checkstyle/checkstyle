package org.checkstyle.suppressionxpathfilter.genericwhitespace;

import java.util.Collections;

public class SuppressionXpathRegressionProcessEnd {
    void bad(Class<? > cls) {//warn
    }
    void good(Class<?> cls) {
    }
}
