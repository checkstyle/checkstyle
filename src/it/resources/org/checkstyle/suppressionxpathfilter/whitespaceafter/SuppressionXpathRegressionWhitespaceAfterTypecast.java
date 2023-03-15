package org.checkstyle.suppressionxpathfilter.whitespaceafter;

public class SuppressionXpathRegressionWhitespaceAfterTypecast {
    Object bad = (Object)null; //warn
    Object good = (Object) null;
}
