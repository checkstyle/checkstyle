package org.checkstyle.suppressionxpathfilter.missingswitchdefault;

public class InputXpathMissingSwitchDefaultOne {
    public static void test1() {
        int key = 2;
        switch (key) { // warn
            case 1:
                break;
            case 2:
                break;
        }
    }
}
