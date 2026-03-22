/*
JavadocVariable
accessModifiers = (default)public,protected,package,private
ignoreNamePattern = (default)null
tokens = (default)ENUM_CONSTANT_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

import java.io.IOException;

class InputJavadocVariableTagsMethods1
{
    // Invalid - should be Javadoc
    private int mMissingJavadoc; // violation, 'Missing a Javadoc comment'

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
     * <p>missing return
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

    /** @throws WrongException problem **/
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

    /** @param WrongParam problem **/
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

}

