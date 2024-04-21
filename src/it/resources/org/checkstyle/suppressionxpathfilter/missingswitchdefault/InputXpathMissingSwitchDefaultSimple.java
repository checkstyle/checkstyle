package org.checkstyle.suppressionxpathfilter.missingswitchdefault;

public class InputXpathMissingSwitchDefaultSimple {
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
