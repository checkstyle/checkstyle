package org.checkstyle.suppressionxpathfilter.anoninnerlength;

import java.util.Comparator;

public class SuppressionXpathRegressionAnonInnerLengthDefault {
    public void test() {
        Runnable runnable = new Runnable() { // warn: inner class is 26 lines (max=20)
            @Override
            public void run() {
                int x = 10;
                String s = "";
                switch (x) {
                    case 1:
                        s = "A";
                        break;
                    case 2:
                        s = "B";
                        break;
                    case 3:
                        s = "C";
                        break;
                    case 4:
                        s = "D";
                        break;
                    case 5:
                        s = "E";
                        break;
                    default:
                        s = "X";
                }
            }
        };
    }
}
