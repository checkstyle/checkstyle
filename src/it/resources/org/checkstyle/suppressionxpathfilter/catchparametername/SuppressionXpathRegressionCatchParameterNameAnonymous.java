package org.checkstyle.suppressionxpathfilter.catchparametername;

public class SuppressionXpathRegressionCatchParameterNameAnonymous {
    class InnerClass {
        InnerClass() {
            new Runnable() {
                @Override
                public void run() {
                    try {
                    } catch (Exception e1) {
                    } try {
                    } catch (Exception E1) { // warn
                    }
                }
            };
        }
    }
}
