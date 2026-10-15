/*
MissingSwitchDefault


*/

package org.checkstyle.suppressionxpathfilter.coding.missingswitchdefault;

public class InputXpathMissingSwitchDefaultSimple {
    public static void test1() {
        int key = 2;
        switch (key) { // violation 'switch without "default" clause.'
            case 1:
                break;
            case 2:
                break;
        }
    }
}
