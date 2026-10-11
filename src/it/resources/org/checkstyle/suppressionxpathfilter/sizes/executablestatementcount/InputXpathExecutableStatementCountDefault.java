package org.checkstyle.suppressionxpathfilter.sizes.executablestatementcount;

public class InputXpathExecutableStatementCountDefault {
    public void ElseIfLadder() { // warn
        if (System.currentTimeMillis() == 0) {
        } else {
            if (System.currentTimeMillis() == 0) {
            } else {
                if (System.currentTimeMillis() == 0) {
                }
            }

            if (System.currentTimeMillis() == 0) {
            }
        }
    }
}
