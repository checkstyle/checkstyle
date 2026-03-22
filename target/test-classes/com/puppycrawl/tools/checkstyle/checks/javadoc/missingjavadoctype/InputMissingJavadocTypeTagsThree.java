/*
MissingJavadocType
scope = PRIVATE
excludeScope = (default)null
skipAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;
import java.io.IOException;

/**
 * Some javadoc.
 */
public class InputMissingJavadocTypeTagsThree {}

// Tests for Javadoc tags.
class InputMissingJavadocTypeTags1Three // violation
{

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
}
