package com.puppycrawl.tools.checkstyle.annotation;

/**
 * @deprecated
 *  stuff
 */
public class BadDeprecatedAnnotation
{
    /**
     * @deprecated        stuff
     */
    protected BadDeprecatedAnnotation() {

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
interface Foo {

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

