package org.checkstyle.suppressionxpathfilter.catchparametername;

public enum SuppressionXpathRegressionCatchParameterNameEnum {
    VALUE {
        @Override
        public void method(String op) {
            switch (op) {
                case "x":
                    try {
                    } catch (Exception eX) { // warn
                    }
                    break;
            }
        }
    };

    public abstract void method(String op);
}
