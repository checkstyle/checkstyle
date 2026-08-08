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

public class InputJavadocTypeTags3
{
    private int mMissingJavadoc;

    /**
     * declaring the imported version of an Exception and documenting
     * the full class name is OK (bug 658805).
     *
     * @throws java.io.IOException if bad things happen.
     */
    void method18()
            throws IOException
    {
        throw new IOException("to make compiler happy");
    }
    /**
     * reverse of bug 658805.
     *
     * @throws IOException if bad things happen.
     */
    void method19()
            throws java.io.IOException
    {
        throw new IOException("to make compiler happy");
    }

    /**
     * Bug 579190, "expected return tag when one is there".
     * <p>
     * Linebreaks after return tag should be legal.
     *
     * @return the bug that states that linebreak should be legal
     */
    int method20() {
        return 579190;
    }

    /**
     * Bug XXXX, "two tags for the same exception"
     *
     * @throws java.io.IOException for some reasons
     * @throws IOException         for another reason
     */
    void method21()
            throws IOException {
    }

    /**
     * RFE 540383, "Unused throws tag for exception subclass"
     *
     * @throws IOException                   for some reasons
     * @throws java.io.FileNotFoundException for another reasons
     */
    void method22()
            throws IOException {
    }

    /**
     * @throws WrongException exception w/o class info but matched by name
     */
    void method23() throws WrongException {
    }

    /**
     * Bug 803577, "allowThrowsTagsForSubclasses/allowMissingThrowsTag interfere"
     * <p>
     * no exception tag for IOException, but here is a tag for its subclass.
     *
     * @throws java.io.FileNotFoundException for another reasons
     */
    void method24() throws IOException {
    }

    /**
     * Bug 841942, "ArrayIndexOutOfBounds in Javadoc handling".
     *
     * @param aParam there is no such param in the method.
     *               The problem should be reported with correct line number.
     */

    void method25() {
    }

    /**
     * {@inheritDoc}
     */
    int method26() {
        return 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return something very important.
     */
    int method27(int aParam) {
        return 0;
    }
}
