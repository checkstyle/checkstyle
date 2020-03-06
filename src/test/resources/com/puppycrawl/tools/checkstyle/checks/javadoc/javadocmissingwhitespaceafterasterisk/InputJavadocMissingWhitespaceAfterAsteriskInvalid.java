package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmissingwhitespaceafterasterisk;



/**Some Javadoc with violation.
 *
 */
class InputJavadocMissingWhitespaceAfterAsteriskInvalid
{
    /**
     *<p>Some Javadoc with violation.
     */
    class InnerInputJavadocMissingWhitespaceAfterAsteriskInValid {

        /**
         *Some Javadoc with violation.
         */
        public static final byte NUL = 0;

        /**
         * Some Javadoc.
         *
         *<p>Some Javadoc with violation.
         */
        void bar1() {}

        /***Some Javadoc with violation. ***/
        void bar2() {}
    }

    /****
     * Some Javadoc.
     *@see Something with violation
     ****/
    void foo1() {}

    /**
     *Some Javadoc with violation.
     **/
    public static final int foo2 = 0;

    /**
     *Some Javadoc with violation.
     */
    enum Foo3 {}

    /**
     *<pre>Some Javadoc with violation.
     *  Some Javadoc.
     *</pre>Some Javadoc with violation.
     **/
    interface Foo4 {}

    /**Some Javadoc with violation. */
    void foo5() {}

    /**@see Something with violation **/
    void foo6() {}

    /** *Some Javadoc with violation. */
    void foo7() {}
}
