/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

/**
 * @deprecated
 *  stuff
 */
public class InputMissingDeprecatedBadDeprecated // violation
{
    /**
     * @deprecated        stuff
     */
    protected InputMissingDeprecatedBadDeprecated() { // violation

    }

    /**
     * @deprecated stuff
     */
    public String toString() { // violation
        return "";
    }

    /**
     * @deprecated stuff
     */
    enum Rock { // violation

        /**
         * @deprecated stuff
         */
        Metallica // violation
    }
}

/**
 * @deprecated stuff
 */
interface Foo1 { // violation

    /**
     * @deprecated stuff
     */
    interface Bar { // violation

    }
}

/**
 * @deprecated stuff
 */
@interface Bleh { // violation

    /**
     * @deprecated stuff
     */
    int bleh(); // violation
}
