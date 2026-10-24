/*
ExecutableStatementCount
max = 0
tokens = (default)CTOR_DEF, METHOD_DEF, INSTANCE_INIT, STATIC_INIT, SLIST, \
         COMPACT_CTOR_DEF, LAMBDA


*/

package org.checkstyle.suppressionxpathfilter.sizes.executablestatementcount;

public class InputXpathExecutableStatementCountDefault {
    public void ElseIfLadder() { // violation 'Executable statement count'
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
