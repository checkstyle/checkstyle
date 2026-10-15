/*
MissingSwitchDefault


*/

package org.checkstyle.suppressionxpathfilter.coding.missingswitchdefault;

public class InputXpathMissingSwitchDefaultStaticBlock {
    static {
        int key = 2;
        switch (key) { // violation 'switch without "default" clause.'
            case 1:
                break;
            case 2:
                break;
        }
    }
}
