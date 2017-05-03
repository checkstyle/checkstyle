package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

/**
 * @deprecated
 *  stuff
 */
public class InputMissingDeprecatedBadDeprecated
{
    /**
     * @deprecated        stuff
     */
    protected InputMissingDeprecatedBadDeprecated() {

    }

    /**
     * @deprecated stuff
     */
    public String toString() {
        return "";
    }

    /**
     * @deprecated stuff
     */
    enum Rock {

        /**
         * @deprecated stuff
         */
        Metallica
    }
}

/**
 * @deprecated stuff
 */
interface Foo1 {

    /**
     * @deprecated stuff
     */
    interface Bar {

    }
}

/**
 * @deprecated stuff
 */
@interface Bleh {

    /**
     * @deprecated stuff
     */
    int bleh();
}
