/*
MissingSwitchDefault


*/

package org.checkstyle.suppressionxpathfilter.coding.missingswitchdefault;

public class InputXpathMissingSwitchDefaultNested {
    public static void test2() {
        int key = 2;
        switch (key) {
            case 1:
                break;
            case 2:
                break;
            default:
                switch (key) { // violation 'switch without "default" clause.'
                    case 2:
                        break;
                }
        }
    }
}
