package org.checkstyle.suppressionxpathfilter.missingswitchdefault;

public class SuppressionXpathRegressionMissingSwitchDefaultTwo {
    public static void test2() {
        int key = 2;
        switch (key) {
            case 1:
                break;
            case 2:
                break;
            default:
                switch (key) { // warn
                    case 2:
                        break;
                }
        }
    }
}
