/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

// violation 5 lines below '.*@java.lang.Deprecated annotation.*@deprecated Javadoc tag.*description.'
/**
 * @deprecated
 *  stuff
 */
public class InputMissingDeprecatedBadDeprecated
{
    // violation 4 lines below '@java.lang.Deprecated annotation.*@deprecated Javadoc tag.*description.'
    /**
     * @deprecated        stuff
     */
    protected InputMissingDeprecatedBadDeprecated() {
    }

    // violation 4 lines below '.*@java.lang.Deprecated annotation.*@deprecated Javadoc.*description.'
    /**
     * @deprecated stuff
     */
    public String toString() {
        return "";
    }

    // violation 4 lines below'.*@java.lang.Deprecated annotation.*@deprecated.*description.'
    /**
     * @deprecated stuff
     */
    enum Rock {

        // violation 4 lines below'.*@java.lang.Deprecated annotation.*@deprecated.*description.'
        /**
         * @deprecated stuff
         */
        Metallica
    }
}

// violation 4 lines below '.*@java.lang.Deprecated annotation.*@deprecated.*description.'
/**
 * @deprecated stuff
 */
interface Foo1 {

    // violation 4 lines below '.*@java.lang.Deprecated annotation.*@deprecated.*description.'
    /**
     * @deprecated stuff
     */
    interface Bar {

    }
}

// violation 4 lines below '.*@java.lang.Deprecated annotation.*@deprecated.*description.'
/**
 * @deprecated stuff
 */
@interface Bleh {

    // violation 4 lines below'.*@java.lang.Deprecated annotation.*@deprecated.*description.'
    /**
     * @deprecated stuff
     */
    int bleh();
}
