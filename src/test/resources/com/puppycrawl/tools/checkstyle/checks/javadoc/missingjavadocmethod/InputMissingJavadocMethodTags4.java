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
class InputMissingJavadocMethodTags4
{
    /**
     * @exception WrongException exception w/o class info but matched by name
     */
    void method23() throws WrongException
    {
    }

    /**
     * Bug 803577, "allowThrowsTagsForSubclasses/allowMissingThrowsTag interfere"
     *
     * no exception tag for IOException, but here is a tag for its subclass.
     * @exception java.io.FileNotFoundException for another reasons
     */
    void method24() throws IOException
    {
    }

    /**
     * Bug 841942, "ArrayIndexOutOfBounds in JavadocStyle".
     * @param aParam there is no such param in the method.
     * The problem should be reported with correct line number.
     */

    void method25()
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

    /**
     * {@inheritDoc}
     *
     * @return 1
     */
    public int foo(Object _arg) {

        return 1;
    }

    /**
     * misplaced @param aParam
     * misplaced @return something very important.
     */
    int method29(int aParam)
    { return 0;
    }
}
