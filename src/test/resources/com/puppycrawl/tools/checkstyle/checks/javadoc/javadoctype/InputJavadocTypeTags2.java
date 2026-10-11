/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;
import java.io.IOException;

public class InputJavadocTypeTags2
{
    private int mMissingJavadoc;

    /**
     * @param aOne
     * @param aTwo   This is a multiline piece of javadoc
     *               Unlike the previous one, it actually has content
     * @param aThree This also has content
     * @param aFour
     * @param aFive
     **/

    void method13(int aOne, int aTwo, int aThree, int aFour, int aFive)
    {
    }

    /**
     * @param aOne Perfectly legal
     **/
    void method14(int aOne)
    {
    }

    /**
     * @throws java.io.IOException just to see if this is also legal
     **/
    void method14()
            throws java.io.IOException
    {
    }

    static {
        int x = 1; // should not require any javadoc
    }


    {
        int z = 2; // should not require any javadoc
    }

    /**
     * handle where variable declaration over several lines
     **/
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

    /**
     * {@inheritDoc}
     **/
    public String toString() {
        return super.toString();
    }

    /**
     * getting code coverage up
     **/
    static final int serialVersionUID = 666;


    /**
     * handle the case of an elaborate header surrounding javadoc comments
     *
     * @param aOne valid parameter content
     */

    void method16(int aOne)
    {
    }

    /**
     * @throws ThreadDeath                  although bad practice, should be silently ignored
     * @throws ArrayStoreException          another r/t subclass
     * @throws IllegalMonitorStateException should be told to remove from throws
     */
    void method17()
            throws IllegalMonitorStateException
    {
    }
}
