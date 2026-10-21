/*
NonEmptyAtclauseDescription
violateExecutionOnNonTightHtml = (default)false
javadocTokens = (default)PARAM_BLOCK_TAG, RETURN_BLOCK_TAG, THROWS_BLOCK_TAG, \
                EXCEPTION_BLOCK_TAG, DEPRECATED_BLOCK_TAG, SINCE_BLOCK_TAG


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.nonemptyatclausedescription;

public class InputNonEmptyAtclauseDescriptionTwo
{
    // violation 7 lines below 'At-clause should have a non-empty description'
    // violation 7 lines below 'At-clause should have a non-empty description'
    // violation 7 lines below 'At-clause should have a non-empty description'
    // violation 7 lines below 'At-clause should have a non-empty description'
    // violation 7 lines below 'At-clause should have a non-empty description'
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

    // violation 7 lines below 'At-clause should have a non-empty description'
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

    // violation 3 lines below 'At-clause should have a non-empty description'
    /**
     * @param a xxx
     * @return
     */
    int foo(int a) {
        return a;
    }

    /**
     * @return {@code 1}
     */     // ^ ok
    int bar() {
        return 1;
    }

    // violation 4 lines below 'At-clause should have a non-empty description'
    // violation 5 lines below 'At-clause should have a non-empty description'
    /**
     *
     * @return
     *
     * @throws Exception
     *
     */
        public int foo7() throws Exception
        {
                return 1;
        }

}
