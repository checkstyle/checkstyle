package org.checkstyle.suppressionxpathfilter.genericwhitespace;

import java.io.Serializable;

public class SuppressionXpathRegressionProcessSingleGenericTwo {
    <E>void bad() {//warn
    }
    <E> void good() {
    }
}
