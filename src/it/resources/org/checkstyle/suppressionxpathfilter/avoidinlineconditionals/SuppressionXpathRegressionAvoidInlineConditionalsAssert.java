package org.checkstyle.suppressionxpathfilter.avoidinlineconditionals;

public class SuppressionXpathRegressionAvoidInlineConditionalsAssert {

    void assertA(String a) {
        // JLS §14.10 - The assert Statement
        // assert Expression1 : Expression2
        assert a.equals(null) ? true : false : "Expression2"; // warn
    }
}
