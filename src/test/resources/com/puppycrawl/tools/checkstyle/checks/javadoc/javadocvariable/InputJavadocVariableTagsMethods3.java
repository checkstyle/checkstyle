/*
JavadocVariable
accessModifiers = (default)public,protected,package,private
ignoreNamePattern = (default)null
tokens = (default)ENUM_CONSTANT_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

import java.io.IOException;

class InputJavadocVariableTagsMethods3
{
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
}


