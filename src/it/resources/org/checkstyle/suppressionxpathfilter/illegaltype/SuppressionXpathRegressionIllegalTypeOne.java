package org.checkstyle.suppressionxpathfilter.illegaltype;

public class SuppressionXpathRegressionIllegalTypeOne {
    public <T extends java.util.HashSet> void typeParam(T t) {} // warn
}
