/*
ParameterNumber
max = 9
ignoreAnnotatedBy = (default)
ignoreOverriddenMethods = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.parameternumber;

/** Test input for ParameterNumberCheck with a raised max. */
final class InputParameterNumberSimple3
{
    /**
     * @param badFormat1 first param
     * @param badFormat2 second param
     * @param badFormat3 third param
     * @throws java.lang.Exception abc
     **/
    int test1(int badFormat1, int badFormat2,
              final int badFormat3)
        throws java.lang.Exception
    {
        return 0;
    }

    /**
     * @see too lazy to document all args. Testing excessive number of args
     **/
    void toManyArgs(int aArg1, int aArg2, int aArg3, int aArg4, int aArg5,
                    int aArg6, int aArg7, int aArg8, int aArg9)
    {
    }
}
