/*
JavadocMissingWhitespaceAfterAsterisk
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmissingwhitespaceafterasterisk;

// violation below 'Missing a whitespace after the leading asterisk.'
/**Some Javadoc with violation.
 */
class InputJavadocMissingWhitespaceAfterAsteriskInvalid
{
    // violation 2 lines below 'Missing a whitespace after the leading asterisk.'
    /**
     *<p>Some Javadoc with violation.
     */
    class InnerInputJavadocMissingWhitespaceAfterAsteriskInValid {

        // violation 2 lines below 'Missing a whitespace after the leading asterisk.'
        /**
         *Some Javadoc with violation.
         */
        public static final byte NUL = 0;

        // violation 3 lines below 'Missing a whitespace after the leading asterisk.'
        /**
         * Some Javadoc.
         *<p>Some Javadoc with violation.
         */
        void bar1() {}

        // violation below 'Missing a whitespace after the leading asterisk.'
        /***Some Javadoc with violation. ***/
        void bar2() {}
    }

    // violation 3 lines below 'Missing a whitespace after the leading asterisk.'
    /****
     * Some Javadoc.
     *@see Something with violation
     ****/
    void foo1() {}

    // violation 2 lines below 'Missing a whitespace after the leading asterisk.'
    /**
     *Some Javadoc with violation.
     **/
    public static final int foo2 = 0;

    // violation 2 lines below 'Missing a whitespace after the leading asterisk.'
    /**
     *Some Javadoc with violation.
     */
    enum Foo3 {}

    // violation 3 lines below 'Missing a whitespace after the leading asterisk.'
    // violation 4 lines below 'Missing a whitespace after the leading asterisk.'
    /**
     *<pre>Some Javadoc with violation.
     *  Some Javadoc.
     *</pre>Some Javadoc with violation.
     **/
    interface Foo4 {}

    // violation below 'Missing a whitespace after the leading asterisk.'
    /**Some Javadoc with violation. */
    void foo5() {}

    // violation below 'Missing a whitespace after the leading asterisk.'
    /**@see Something with violation **/
    void foo6() {}

    // violation below 'Missing a whitespace after the leading asterisk.'
    /** *Some Javadoc with violation. */
    void foo7() {}
}
