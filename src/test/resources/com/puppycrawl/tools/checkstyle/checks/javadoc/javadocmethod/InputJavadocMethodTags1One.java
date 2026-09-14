/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/**
 * Config:
 * validateThrows = true
 */
public class InputJavadocMethodTags1One {
    // Invalid - should be Javadoc
    private int mMissingJavadoc;

    // Invalid - should be Javadoc
    void method1()
    {
    }

    // violation below 'Unused @param tag for 'unused'.'
    /** @param unused asd **/
    void method2()
    {
    }

    /** missing return **/
    int method3() // violation '@return tag should be present and have description.'
    {
        return 3;
    }

    /**
     * <p>missing return
     * @param aOne ignored
     **/
    int method4(int aOne) // violation '@return tag should be present and have description.'
    {
        return aOne;
    }

    /** missing throws **/
    void method5()
        throws Exception // violation 'Expected @throws tag for 'Exception'.'
    {}

    /**
     * @see missing throws
     * @see need to see tags to avoid shortcut logic
     **/
    void method6()
        throws Exception // violation 'Expected @throws tag for 'Exception'.'
    {
    }

    /** @throws WrongException problem **/
    void method7()
        throws Exception, NullPointerException // 2 violations
    {
    }

    /** missing param **/
    void method8(int aOne) // violation 'Expected @param tag for 'aOne'.'
    {
    }

    /**
     * @see missing param
     * @see need to see tags to avoid shortcut logic
     **/
    void method9(int aOne) // violation 'Expected @param tag for 'aOne'.'
    {
    }

    // violation below 'Unused @param tag for 'WrongParam'.'
    /** @param WrongParam problem **/
    void method10(int aOne, int aTwo) // 2 violations
    {
    }

    // violation 3 lines below 'Unused @param tag for 'Unneeded'.'
    // violation 3 lines below 'Unused Javadoc tag.'
    /**
     * @param Unneeded parameter
     * @return also unneeded
     **/
    void method11()
    {
    }

    // violation 3 lines below 'Duplicate @return tag.'
    /**
     * @return first one
     * @return duplicate
     **/
    int method12()
    {
        return 0;
    }

    /**
     * Documenting different causes for the same exception
     * in separate tags is OK (bug 540384).
     *
     * @throws java.io.IOException if A happens
     * @throws java.io.IOException if B happens
     **/
    void method13()
       throws java.io.IOException
    {
    }
}
