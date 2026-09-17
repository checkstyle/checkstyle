/*
WhitespaceBeforeEmptyBody
tokens = LITERAL_SYNCHRONIZED


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

public class InputWhitespaceBeforeEmptyBodySynchronized {

    void test() {
        synchronized (this){   // violation ''{' is not preceded with whitespace'
        }

        synchronized (this){}  // violation ''{' is not preceded with whitespace'

        synchronized (this){
            int x = 0;
        }
    }
}
