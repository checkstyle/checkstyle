package org.checkstyle.suppressionxpathfilter.genericwhitespace;

import java.util.Collections;

public class SuppressionXpathRegressionGenericWhitespaceStartThree {
    < E> void bad() {//warn
    }
    <E> void good() {
    }
}
