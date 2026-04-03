/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

/**
 * Test case for detecting missing final parameters.
 * @author Lars Kühne
 **/
class InputFinalParameters2
{
    /** no param constructor */
    InputFinalParameters2()
    {
    }

    /** non final param constructor */
    InputFinalParameters2(String s) // violation, 's' should be final
    {
    }

    /** non final param constructor */
    InputFinalParameters2(final Integer i)
    {
    }

    /** final param constructor with annotation */
    InputFinalParameters2(final @MyAnnotation33 Class<Object> i)
    {
    }

    /** non-final param constructor with annotation*/
    InputFinalParameters2(@MyAnnotation33 Boolean i) // violation, 'i' should be final
    {
    }

    /** mixed */
    InputFinalParameters2(String s, final Integer i) // violation, 's' should be final
    {
    }
}
