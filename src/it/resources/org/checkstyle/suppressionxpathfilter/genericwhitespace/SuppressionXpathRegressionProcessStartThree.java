package org.checkstyle.suppressionxpathfilter.genericwhitespace;

import java.util.Collections;

public class SuppressionXpathRegressionProcessStartThree {
    < E> void bad() {//warn
    }
    <E> void good() {
    }
}
