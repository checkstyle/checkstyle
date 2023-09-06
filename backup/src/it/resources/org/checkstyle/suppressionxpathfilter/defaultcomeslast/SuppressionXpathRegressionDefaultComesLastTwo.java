package org.checkstyle.suppressionxpathfilter.defaultcomeslast;

public class SuppressionXpathRegressionDefaultComesLastTwo {
    public void test(int i) {
        switch (i) {
            case 1:
            default:
                break;
            case 2:
                break;
        }

        switch (i) {
            case 1:
            default: //warn
            case 2:
                break;
            case 3:
                break;
        }
    }
}
