package org.checkstyle.suppressionxpathfilter.genericwhitespace;

import java.util.Collections;

public class SuppressionXpathRegressionProcessSingleGenericOne {
    Object bad = Collections.<Object> emptySet();//warn
    Object good = Collections.<Object>emptySet();
}
