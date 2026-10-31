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

public class InputJavadocMethodTags1Three {
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

    // violation 3 lines below 'Un.* @param .* 'aParam'.'
    /**
     * Bug 841942, "ArrayIndexOutOfBounds in Javadoc handling".
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
    // 2 violations above:
    //    '@return tag should be present and have description.'
    //    'Expected @param tag for 'aParam'.'
    { return 0;
    }
}
