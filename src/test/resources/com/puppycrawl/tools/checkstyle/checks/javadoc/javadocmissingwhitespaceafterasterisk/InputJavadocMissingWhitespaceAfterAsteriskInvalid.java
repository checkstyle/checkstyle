/*
JavadocMissingWhitespaceAfterAsterisk
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmissingwhitespaceafterasterisk;

/**Some Javadoc with violation. // violation 'Missing a whitespace after the leading asterisk.'
 *
 */
class InputJavadocMissingWhitespaceAfterAsteriskInvalid
{
    /**
     *<p>Some Javadoc with violation. // violation 'Missing a whitespace after the leading asterisk.'
     */
    class InnerInputJavadocMissingWhitespaceAfterAsteriskInValid {

        /**
         *Some Javadoc with violation. // violation 'Missing a whitespace after the leading asterisk.'
         */
        public static final byte NUL = 0;

        /**
         * Some Javadoc.
         *
         *<p>Some Javadoc with violation. // violation 'Missing a whitespace after the leading asterisk.'
         */
        void bar1() {}

        /***Some Javadoc with violation. ***/ // violation  'Missing a whitespace after the leading asterisk.'
        void bar2() {}
    }

    /****
     * Some Javadoc.
     *@see Something with violation // violation 'Missing a whitespace after the leading asterisk.'
     ****/
    void foo1() {}

    /**
     *Some Javadoc with violation. // violation 'Missing a whitespace after the leading asterisk.'
     **/
    public static final int foo2 = 0;

    /**
     *Some Javadoc with violation. // violation 'Missing a whitespace after the leading asterisk.'
     */
    enum Foo3 {}

    /**
     *<pre>Some Javadoc with violation. // violation 'Missing a whitespace after the leading asterisk.'
     *  Some Javadoc.
     *</pre>Some Javadoc with violation. // violation 'Missing a whitespace after the leading asterisk.'
     **/
    interface Foo4 {}

    /**Some Javadoc with violation. */ // violation 'Missing a whitespace after the leading asterisk.'
    void foo5() {}

    /**@see Something with violation **/ // violation 'Missing a whitespace after the leading asterisk.'
    void foo6() {}

    /** *Some Javadoc with violation. */ // violation 'Missing a whitespace after the leading asterisk.'
    void foo7() {}
}
