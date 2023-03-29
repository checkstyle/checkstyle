/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

/**
 * @deprecated
 *  stuff
 */ // violation below '.*@java.lang.Deprecated annotation.*@deprecated Javadoc tag.*description.'
public class InputMissingDeprecatedBadDeprecated
{
    /**
     * @deprecated        stuff
     */
    protected InputMissingDeprecatedBadDeprecated() {
    // violation above '.*@java.lang.Deprecated annotation.*@deprecated Javadoc tag.*description.'
    }

    /**
     * @deprecated stuff
     */ // violation below '.*@java.lang.Deprecated annotation.*@deprecated Javadoc.*description.'
    public String toString() {
        return "";
    }

    /**
     * @deprecated stuff
     */
    enum Rock { // violation '.*@java.lang.Deprecated annotation.*@deprecated.*description.'

        /**
         * @deprecated stuff
         */
        Metallica // violation '.*@java.lang.Deprecated annotation.*@deprecated.*description.'
    }
}

/**
 * @deprecated stuff
 */
interface Foo1 { // violation '.*@java.lang.Deprecated annotation.*@deprecated.*description.'

    /**
     * @deprecated stuff
     */
    interface Bar { // violation '.*@java.lang.Deprecated annotation.*@deprecated.*description.'

    }
}

/**
 * @deprecated stuff
 */
@interface Bleh { // violation '.*@java.lang.Deprecated annotation.*@deprecated.*description.'

    /**
     * @deprecated stuff
     */
    int bleh(); // violation '.*@java.lang.Deprecated annotation.*@deprecated.*description.'
}
