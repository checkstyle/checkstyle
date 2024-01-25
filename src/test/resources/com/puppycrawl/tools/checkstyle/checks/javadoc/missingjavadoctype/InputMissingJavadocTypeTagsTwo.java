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
public class InputMissingJavadocTypeTagsTwo {}

// Tests for Javadoc tags.
class InputMissingJavadocTypeTags1 // violation
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

    /** {@inheritDoc} */
    int method26()
    { return 0;
    }

     /**
     * {@inheritDoc}
     * @return something very important.
     */
    int method27(int aParam)
    { return 0;
    }

    /**
     * @return something very important.
     * {@inheritDoc}
     */
    int method28(int aParam)
    { return 0;
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

    /** {@inheritDoc} **/
    public String toString()
    {
        return super.toString();
    }

    /** getting code coverage up **/
    static final int serialVersionUID = 666;

    /**
     * {@inheritDoc}
     *
     * @return 1
     */
    public int foo(Object _arg) {

        return 1;
    }
}

/**
 *  Added to make this file compilable.
 */
class WrongException extends RuntimeException
{
}
