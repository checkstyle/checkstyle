/*
NonEmptyAtclauseDescription
violateExecutionOnNonTightHtml = (default)false
javadocTokens = (default)PARAM_LITERAL, RETURN_LITERAL, THROWS_LITERAL, \
                EXCEPTION_LITERAL, DEPRECATED_LITERAL


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.nonemptyatclausedescription;

public class InputNonEmptyAtclauseDescriptionTwo
{
    /**
     *
     * @param a
     * @param b
     * @param c
     * @deprecated
     * @throws Exception
     */ // violation 5 lines above 'At-clause should have a non-empty description'
    // violation 5 lines above 'At-clause should have a non-empty description'
    // violation 5 lines above 'At-clause should have a non-empty description'
    // violation 5 lines above 'At-clause should have a non-empty description'
    // violation 5 lines above 'At-clause should have a non-empty description'
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
     */ // violation above 'At-clause should have a non-empty description'
    public int foo6(String a, int b, double c) throws Exception
    {
        return 1;
    }

    /**
     * @param a xxx
     * @return
     */ // violation above 'At-clause should have a non-empty description'
    int foo(int a) {
        return a;
    }

    /**
     * @return {@code 1}
     */     // ^ ok
    int bar() {
        return 1;
    }

    /**
     *
     * @return
     *
     * @throws Exception
     *
     */ // violation 4 lines above 'At-clause should have a non-empty description'
    // violation 3 lines above 'At-clause should have a non-empty description'
        public int foo7() throws Exception
        {
                return 1;
        }

    /**
     *
     * @param
     *
     */ // violation 2 lines above 'At-clause should have a non-empty description'
        public int foo8(int a)
        {
                return 1;
        }

    /**
     * @throws
     */ // violation above 'At-clause should have a non-empty description'
        public int foo9(String a) throws Exception
        {
            return 1;
        }
}
