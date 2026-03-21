/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

import java.io.IOException;

public class InputJavadocMethodTags1Two {
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
    void method14(int aOne, int aTwo, int aThree, int aFour, int aFive)
    {
    }

    /** @param aOne Perfectly legal **/
    void method15(int aOne)
    {
    }

    /** @throws java.io.IOException
     *               just to see if this is also legal **/
    void method15()
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

    /** {@inheritDoc} **/
    public String toString()
    {
        return super.toString();
    }

    /** getting code coverage up **/
    static final int serialVersionUID = 666;

    //**********************************************************************/
    // Method Name: method16
    /**
     * handle the case of an elaborate header surrounding javadoc comments
     *
     * @param aOne valid parameter content
     */
    //**********************************************************************/
    void method16(int aOne)
    {
    }


    /**
     * @throws ThreadDeath although bad practice, should be silently ignored
     * @throws ArrayStoreException another r/t subclass
     * @throws IllegalMonitorStateException should be told to remove from throws
     */
    void method17()
        throws IllegalMonitorStateException
    {
    }

    /**
     * declaring the imported version of an Exception and documenting
     * the full class name is OK (bug 658805).
     * @throws java.io.IOException if bad things happen.
     */
    void method18()
        throws IOException
    {
        throw new IOException("to make compiler happy");
    }

     /**
     * reverse of bug 658805.
     * @throws IOException if bad things happen.
     */
    void method19()
        throws java.io.IOException
    {
        throw new IOException("to make compiler happy");
    }
}
