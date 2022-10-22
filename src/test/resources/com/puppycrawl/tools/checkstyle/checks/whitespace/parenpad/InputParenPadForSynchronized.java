/*
ParenPad
option = (default)nospace
tokens = LITERAL_SYNCHRONIZED, CTOR_DEF, CTOR_CALL, LITERAL_IF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

/**
 *
 * Thread-safe Singleton class.
 * The instance is lazily initialized and thus needs synchronization
 * mechanism.
 *
 */
class InputParenPadForSynchronized {

    private static InputParenPadForSynchronized instance = null;
    private InputParenPadForSynchronized() {
    }

    public synchronized static InputParenPadForSynchronized getInstance() {
        /*
         * The instance gets created only when it is called for first time.
         * Lazy -loading
         */
        if(instance == null) {
            instance = new InputParenPadForSynchronized();
        }
        return instance;
    }
}
