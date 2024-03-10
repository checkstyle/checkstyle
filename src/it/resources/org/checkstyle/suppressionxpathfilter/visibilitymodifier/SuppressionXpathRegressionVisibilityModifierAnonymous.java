package org.checkstyle.suppressionxpathfilter.visibilitymodifier;

public class SuppressionXpathRegressionVisibilityModifierAnonymous {
    private Runnable runnable = new Runnable() {

        public String field1; // warn

        @Override
        public void run() {

        }
    };
}
