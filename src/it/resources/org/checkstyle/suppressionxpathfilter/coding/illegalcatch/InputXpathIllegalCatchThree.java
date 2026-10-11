package org.checkstyle.suppressionxpathfilter.coding.illegalcatch;

public class InputXpathIllegalCatchThree {

    static {
        try {
            int x = 0;
        } catch (Throwable e ) { // warn
        }
    }
}
