/*
NonEmptyAtclauseDescription
violateExecutionOnNonTightHtml = (default)false
javadocTokens = (default)PARAM_BLOCK_TAG, RETURN_BLOCK_TAG, THROWS_BLOCK_TAG, \
         EXCEPTION_BLOCK_TAG, DEPRECATED_BLOCK_TAG, SINCE_BLOCK_TAG


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.nonemptyatclausedescription;

class InputNonEmptyAtclauseDescriptionSince
{
        /**
         * Some javadoc
         * @param a Some javadoc
         * @since 1.0
         */
        public void fooWithSince(String a)
        {

        }

        // violation 4 lines below 'At-clause should have a non-empty description'
        /**
         * Some javadoc
         * @param a Some javadoc
         * @since
         */
        public void fooWithEmptySince(String a)
        {

        }
}
