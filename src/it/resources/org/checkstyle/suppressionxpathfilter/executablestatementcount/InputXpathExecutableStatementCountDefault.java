package org.checkstyle.suppressionxpathfilter.executablestatementcount;

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
