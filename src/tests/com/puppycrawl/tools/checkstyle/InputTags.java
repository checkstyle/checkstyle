////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

// Tests for Javadoc tags.
class InputTags
{
    // Invalid - should be Javadoc
    private int mMissingJavadoc;

    // Invalid - should be Javadoc
    void method1()
    {
    }

    /** @param unused asd **/
    void method2()
    {
    }

    /** missing return **/
    int method3()
    {
        return 3;
    }

    /**
     * missing return
     * @param aOne ignored
     **/
    int method4(int aOne)
    {
        return aOne;
    }

    /** missing throws **/
    void method5()
        throws Exception
    {
    }

    /**
     * @see missing throws
     * @see need to see tags to avoid shortcut logic
     **/
    void method6()
        throws Exception
    {
    }

    /** @throws WrongException error **/
    void method7()
        throws Exception, NullPointerException
    {
    }

    /** missing param **/
    void method8(int aOne)
    {
    }

    /**
     * @see missing param
     * @see need to see tags to avoid shortcut logic
     **/
    void method9(int aOne)
    {
    }

    /** @param WrongParam error **/
    void method10(int aOne, int aTwo)
    {
    }

    /**
     * @param Unneeded parameter
     * @return also unneeded
     **/
    void method11()
    {
    }

    /**
     * @return first one
     * @return duplicate
     **/
    int method12()
    {
        return 0;
    }

    /**
     * @param aOne
     * @param aTwo
     *
     *     This is a multiline piece of javadoc
     *     Unlike the previous one, it actually has content
     * @param aThree
     *
     *
     *     This also has content
     * @param aFour

     *
     * @param aFive
     **/
    void method13(int aOne, int aTwo, int aThree, int aFour, int aFive)
    {
    }

    /** @param aOne Perfectly legal **/
    void method14(int aOne)
    {
    }

    /** @throws java.io.IOException
     *               just to see if this is also legal **/
    void method14()
       throws java.io.IOException
    {
    }



    // Test static initialiser
    static
    {
        int x = 1; // should not require any javadoc
    }

    // test initialiser
    {
        int z = 2; // should not require any javadoc
    }

    /** handle where variable declaration over several lines **/
    private static final int
        ON_SECOND_LINE = 2;


    /**
     * Documenting different causes for the same exception
     * in separate tags is OK (bug 540384).
     *
     * @throws java.io.IOException if A happens
     * @throws java.io.IOException if B happens
     **/
    void method15()
       throws java.io.IOException
    {
    }
}
