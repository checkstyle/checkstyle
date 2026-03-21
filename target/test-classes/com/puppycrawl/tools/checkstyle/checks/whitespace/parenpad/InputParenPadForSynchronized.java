/*
ParenPad
option = (default)nospace
tokens = LITERAL_SYNCHRONIZED, CTOR_DEF, CTOR_CALL, LITERAL_IF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

class InputParenPadForSynchronized {

    private static InputParenPadForSynchronized instance = null;
    private InputParenPadForSynchronized() {
    }

    public synchronized static InputParenPadForSynchronized getInstance() {
        if(instance == null ) { // violation, '')' is preceded with whitespace.'
            instance = new InputParenPadForSynchronized();
        }
        return instance;
    }
}
