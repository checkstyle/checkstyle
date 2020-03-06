package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmissingwhitespaceafterasterisk;



/***
 * Some Javadoc.
 *
 * <p>Some Javadoc.
 ***/
class InputJavadocMissingWhitespaceAfterAsteriskValid
{
    /**
     * Some Javadoc.
     *
     * <p>Some Javadoc.
     *
     * @see Something
     **/
    class InnerInputJavadocMissingWhitespaceAfterAsteriskValid {

        /**
         * Some Javadoc.
         */
        public static final byte NUL = 0;

        /**
         * Some Javadoc.
         *
         * <p>Some Javadoc.
         */
        void bar1() {}

        /******** Some Javadoc. *******/
        void bar2() {}
    }

    /****
     *
     * Some Javadoc.
     * @see Something
     ****/
    void foo1() {}


    /**
     * Some Javadoc.
     */
    public static final int foo2 = 0;

    /**
     * Some javadoc.
     */
    enum Foo3 {}

    /**
     * <pre>
     *   Some javadoc.
     * </pre>
     **/
    interface Foo4 {}

    /***/
    void foo5() {}

    /** Some Javadoc. **/
    void foo6() {}

    /** * Some Javadoc. */
    void foo7() {}
}
