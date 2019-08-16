package org.checkstyle.suppressionxpathfilter.genericwhitespace;

import java.io.Serializable;

public class SuppressionXpathRegressionGenericWhitespaceSingleGenericTwo {
    <E>void bad() {//warn
    }
    <E> void good() {
    }
}
