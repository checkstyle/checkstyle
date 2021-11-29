/*
NonEmptyAtclauseDescription
violateExecutionOnNonTightHtml = (default)false
javadocTokens = (default)PARAM_LITERAL, RETURN_LITERAL, THROWS_LITERAL, \
                EXCEPTION_LITERAL, DEPRECATED_LITERAL


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.nonemptyatclausedescription;

class InputNonEmptyAtclauseDescription
{
        /**
         * Some javadoc.
         * @param a Some javadoc.
         * @param b Some javadoc.
         */
        public InputNonEmptyAtclauseDescription(String a, int b)
        {

        }

        /**
         * Some javadoc.
         * @param a Some javadoc. // ok
         * @deprecated Some javadoc.
         */
        public InputNonEmptyAtclauseDescription(String a)
        {

        }

        // violation 3 lines below
        /**
         * Some javadoc.
         * @param a
         * @param b
         * @param c
         */ // violation 2 lines above
        // violation 2 lines above
        public InputNonEmptyAtclauseDescription(String a, int b, double c)
        {

        }

        // violation 3 lines below
        /**
         *
         * @param a
         * @param e
         * @deprecated
         */ // violation 2 lines above
        // violation 2 lines above
        public InputNonEmptyAtclauseDescription(String a, boolean e)
        {

        }

        /**
         * Some javadoc
         * @param a Some javadoc
         * @param b Some javadoc
         * @param c Some javadoc
         * @return Some javadoc
         * @throws Exception Some javadoc
         * @deprecated Some javadoc
         */
        public int foo1(String a, int b, double c) throws Exception
        {
                return 1;
        }

        /**
         *
         * @param a Some javadoc
         * @param b Some javadoc
         * @param c Some javadoc
         * @return Some javadoc
         * @throws Exception Some javadoc
         */
        public int foo2(String a, int b, double c) throws Exception
        {
                return 1;
        }

        // violation 5 lines below
        // violation 5 lines below
        // violation 5 lines below
        /**
         *
         * @param a
         * @param b
         * @param c
         * @deprecated
         * @throws Exception
         * @deprecated
         */ // violation 3 lines above
        // violation 3 lines above
        // violation 3 lines above
        public int foo3(String a, int b, double c) throws Exception
        {
                return 1;
        }

        // violation 4 lines below
        // violation 4 lines below
        /**
         *
         * @param a
         * @param b
         * @param c
         * @deprecated
         * @throws Exception
         */ // violation 3 lines above
        // violation 3 lines above
        // violation 3 lines above
        public int foo4(String a, int b, double c) throws Exception
        {
                return 1;
        }

        /**
         * Some javadoc
         * @param a Some javadoc
         * @param b Some javadoc
         * @param c Some javadoc
         * @return Some javadoc
         * @exception Exception Some javadoc
         * @deprecated Some javadoc
         */
        public int foo5(String a, int b, double c) throws Exception
        {
                return 1;
        }

        /**
         *
         * @param a Some javadoc
         * @param b Some javadoc
         * @param c Some javadoc
         * @return Some javadoc
         * @exception Exception
         */ // violation above
        public int foo6(String a, int b, double c) throws Exception
        {
                return 1;
        }

        /**
         * @param a xxx
         * @return
         */ // violation above
        int foo(int a) {
                return a;
        }

        /**
         * @return {@code 1}
         */     // ^ ok
        int bar() {
                return 1;
        }
}
