/*
AnonInnerLength
max = (default)20


*/

package org.checkstyle.suppressionxpathfilter.sizes.anoninnerlength;

import java.util.Comparator;

public class InputXpathAnonInnerLengthDefault {
    public void test() {
        // violation below 'Anonymous inner class length is 26 lines (max allowed is 20).'
        Runnable runnable = new Runnable() {
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
