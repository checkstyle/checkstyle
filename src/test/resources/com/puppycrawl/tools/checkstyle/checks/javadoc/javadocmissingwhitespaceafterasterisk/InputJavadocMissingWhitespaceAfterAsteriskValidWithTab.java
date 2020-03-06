package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmissingwhitespaceafterasterisk;



/***
 *	Some Javadoc with tab.
 *
 *	<p>Some Javadoc with tab.
 ***/
class InputJavadocMissingWhitespaceAfterAsteriskValidWithTab
{
    /**
     *	Some Javadoc with tab.
     *
     *	<p>Some Javadoc with tab.
     *
     *	@see	Something with tab
     **/
    class InnerInputJavadocMissingWhitespaceAfterAsteriskValid {

        /**
         *	Some Javadoc with tab.
         */
        public static final byte NUL = 0;

        /**
         *	Some Javadoc with tab.
         *
         *	<p>Some Javadoc with tab.
         */
        void bar1() {}

        /********	Some Javadoc with tab. *******/
        void bar2() {}
    }

    /****
     *
     *	Some	Javadoc with tab.
     *	@see Something with tab
     ****/
    void foo1() {}


    /**
     *	Some Javadoc with tab.
     */
    public static final int foo2 = 0;

    /**
     *	Some javadoc with tab.
     */
    enum Foo3 {}

    /**
     * <pre>
     *		Some javadoc with tab.
     * </pre>
     **/
    interface Foo4 {}

    /**	*/
    void foo5() {}

    /**	Some	Javadoc with tab. **/
    void foo6() {}

    /**	*	Some Javadoc with tab. */
    void foo7() {}
}
