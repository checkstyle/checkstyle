/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
ignoreMethodsWithImplementation = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;
import java.io.IOException;
// Tests for Javadoc tags.
class InputMissingJavadocMethodTags1
{
    // Invalid - should be Javadoc
    private int mMissingJavadoc;

    // Invalid - should be Javadoc
    void method1() // violation 'Missing a Javadoc comment.'
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
     */
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
     */
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
     */
    void method9(int aOne)
    {
    }

    /** @param WrongParam problem **/
    void method10(int aOne, int aTwo)
    {
    }
}
