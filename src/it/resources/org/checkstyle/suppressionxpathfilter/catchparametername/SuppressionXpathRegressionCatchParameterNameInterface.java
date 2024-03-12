package org.checkstyle.suppressionxpathfilter.catchparametername;

interface SuppressionXpathRegressionCatchParameterNameInterface {
    interface InnerInterface {
        default void method() {
            try {
            } catch (Exception EX) { // warn
            }
        }
    }
}
