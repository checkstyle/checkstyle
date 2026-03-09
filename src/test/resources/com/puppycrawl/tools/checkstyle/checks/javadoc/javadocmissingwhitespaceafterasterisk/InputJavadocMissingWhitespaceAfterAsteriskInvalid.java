/*
JavadocMissingWhitespaceAfterAsterisk
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmissingwhitespaceafterasterisk;

/**Some Javadoc with violation.
 * // violation above 'Missing a whitespace after the leading asterisk.'
 */
class InputJavadocMissingWhitespaceAfterAsteriskInvalid
{
    /** // violation below 'Missing a whitespace after the leading asterisk.'
     *<p>Some Javadoc with violation.
     */
    class InnerInputJavadocMissingWhitespaceAfterAsteriskInValid {

        /** // violation below 'Missing a whitespace after the leading asterisk.'
         *Some Javadoc with violation.
         */
        public static final byte NUL = 0;

        /**
         * Some Javadoc.
         * // violation below 'Missing a whitespace after the leading asterisk.'
         *<p>Some Javadoc with violation.
         */
        void bar1() {}

        // violation below 'Missing a whitespace after the leading asterisk.'
        /***Some Javadoc with violation. ***/
        void bar2() {}
    }

    /****
     * Some Javadoc. // violation below 'Missing a whitespace after the leading asterisk.'
     *@see Something with violation
     ****/
    void foo1() {}

    /** // violation below 'Missing a whitespace after the leading asterisk.'
     *Some Javadoc with violation.
     **/
    public static final int foo2 = 0;

    /** // violation below 'Missing a whitespace after the leading asterisk.'
     *Some Javadoc with violation.
     */
    enum Foo3 {}

    /** // violation below 'Missing a whitespace after the leading asterisk.'
     *<pre>Some Javadoc with violation.
     *  Some Javadoc. // violation below 'Missing a whitespace after the leading asterisk.'
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
