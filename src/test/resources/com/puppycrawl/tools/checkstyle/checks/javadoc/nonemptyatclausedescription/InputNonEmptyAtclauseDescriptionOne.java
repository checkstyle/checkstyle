/*
NonEmptyAtclauseDescription
violateExecutionOnNonTightHtml = (default)false
javadocTokens = (default)PARAM_BLOCK_TAG, THROWS_BLOCK_TAG, THROWS_BLOCK_TAG, \
                EXCEPTION_BLOCK_TAG, DEPRECATED_BLOCK_TAG


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.nonemptyatclausedescription;

class InputNonEmptyAtclauseDescriptionOne
{
        /**
         * Some javadoc.
         * @param a Some javadoc.
         * @param b Some javadoc.
         */
        public InputNonEmptyAtclauseDescriptionOne(String a, int b)
        {

        }

        /**
         * Some javadoc.
         * @param a Some javadoc.
         * @deprecated Some javadoc.
         */
        public InputNonEmptyAtclauseDescriptionOne(String a)
        {

        }

        // violation 3 lines below 'At-clause should have a non-empty description'
        /**
         * Some javadoc.
         * @param a
         * @param b
         * @param c
         */ // violation 2 lines above 'At-clause should have a non-empty description'
        // violation 2 lines above 'At-clause should have a non-empty description'
        public InputNonEmptyAtclauseDescriptionOne(String a, int b, double c)
        {

        }

        // violation 3 lines below 'At-clause should have a non-empty description'
        /**
         *
         * @param a
         * @param e
         * @deprecated       
         */ // violation 2 lines above 'At-clause should have a non-empty description'
        // violation 2 lines above 'At-clause should have a non-empty description'
        public InputNonEmptyAtclauseDescriptionOne(String a, boolean e)
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

        // violation 5 lines below 'At-clause should have a non-empty description'
        // violation 5 lines below 'At-clause should have a non-empty description'
        // violation 5 lines below 'At-clause should have a non-empty description'
        /**
         *
         * @param a
         * @param b
         * @param c
         * @deprecated
         * @throws Exception
         * @deprecated              
         * 
         */ // violation 4 lines above 'At-clause should have a non-empty description'
        // violation 4 lines above 'At-clause should have a non-empty description'
        // violation 4 lines above 'At-clause should have a non-empty description'
        public int foo3(String a, int b, double c) throws Exception
        {
                return 1;
        }
}
