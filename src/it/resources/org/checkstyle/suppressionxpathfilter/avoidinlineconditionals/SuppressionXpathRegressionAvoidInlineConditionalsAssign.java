package org.checkstyle.suppressionxpathfilter.avoidinlineconditionals;

public class SuppressionXpathRegressionAvoidInlineConditionalsAssign {
    String b;

    void setB(String a) {
        b = (a == null || a.length() < 1) ? null : a.substring(1); // warn
    }
}
