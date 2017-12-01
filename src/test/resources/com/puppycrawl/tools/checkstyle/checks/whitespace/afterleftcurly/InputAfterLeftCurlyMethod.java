package com.puppycrawl.tools.checkstyle.checks.whitespace.afterleftcurly;

/**
 * Method content test cases for AfterLeftCurlyCheck.
 */
class InputAfterLeftCurlyMethod
{
    /** METHOD_DEF */
    void emptyMethodBody()
    {
    }

    int methodNoEmptyLine() {
        return 1;
    }

    int methodEmptyLine() {

        return 1;
    }

    /** LAMBDA */
    Runnable emptyLambda = () -> {};

    Runnable lambdaNoEmptyLine = () -> {
        String.valueOf("");
    };

    Runnable lambdaEmptyLine = () ->
    {

        String.valueOf("");
    };

    /** STATIC_INIT */
    static {}

    static {
        // No empty line
    }

    static
    {

        // Empty line
    }

    /** INSTANCE_INIT */
    {
    }

    {
        // No empty line
    }

    {
        
        // The trailing spaces before this comment are left intentionally.
    }

    /** SLIST */
    void scopeBlock()
    {
        {}

        { String.valueOf("no empty line"); }

        {

            String.valueOf("empty line"); }
    }

    /** CTOR_DEF */
    InputAfterLeftCurlyMethod() {}

    InputAfterLeftCurlyMethod(int noEmptyLine) {
        scopeBlock();
    }

    InputAfterLeftCurlyMethod(boolean emptyLine) {

        scopeBlock();
    }

    /** ARRAY_INIT */
    final int[] emptyArray = new int[] { };

    final int[][] arrayNoEmptyLine = new int[][] {
            {0},{1}
    };

    final int[][] arrayEmptyLine = new int[][] {

        {

            0 },{

            1
        }
    };

}
