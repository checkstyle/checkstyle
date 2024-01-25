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
public class InputMissingJavadocTypeTagsFour {}

// Tests for Javadoc tags.
class InputMissingJavadocTypeTags1Four // violation
{
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

    /**
     * Bug 579190, "expected return tag when one is there".
     *
     * Linebreaks after return tag should be legal.
     *
     * @return
     *   the bug that states that linebreak should be legal
     */
    int method20()
    {
        return 579190;
    }

    /**
     * Bug XXXX, "two tags for the same exception"
     *
     * @exception java.io.IOException for some reasons
     * @exception IOException for another reason
     */
    void method21()
       throws IOException
    {
    }

    /**
     * RFE 540383, "Unused throws tag for exception subclass"
     *
     * @exception IOException for some reasons
     * @exception java.io.FileNotFoundException for another reasons
     */
    void method22()
       throws IOException
    {
    }
}
