package org.checkstyle.suppressionxpathfilter.coding.missingswitchdefault;

public class InputXpathMissingSwitchDefaultStaticBlock {
    static {
        int key = 2;
        switch (key) { // warn
            case 1:
                break;
            case 2:
                break;
        }
    }
}
