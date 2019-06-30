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
         * @param a Some javadoc.
         * @deprecated Some javadoc.
         */
        public InputNonEmptyAtclauseDescription(String a)
        {

        }

        /**
         * Some javadoc.
         * @param a
         * @param b
         * @param c
         */
        public InputNonEmptyAtclauseDescription(String a, int b, double c)
        {

        }

        /**
         *
         * @param a
         * @param e
         * @deprecated
         */
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

        /**
         *
         * @param a
         * @param b
         * @param c
         * @deprecated
         * @throws Exception
         * @deprecated
         */
        public int foo3(String a, int b, double c) throws Exception
        {
                return 1;
        }

        /**
         *
         * @param a
         * @param b
         * @param c
         * @deprecated
         * @throws Exception
         */
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
         */
        public int foo6(String a, int b, double c) throws Exception
        {
                return 1;
        }
}
