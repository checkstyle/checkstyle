/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = true
allowMissingReturnTag = true
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodMissingJavadocNoMissingTags {
    /**
     * Missing return.
     *
     * @param number to return
     * @throws ThreadDeath sometimes
     */
    int missingReturn(int number) throws ThreadDeath { // ok
        return number;
    }

    /**
     * Missing param.
     *
     * @return number
     * @throws ThreadDeath sometimes
     */
    int missingParam(int number) throws ThreadDeath { // ok
        return number;
    }

    /**
     * Missing throws.
     *
     * @param number to return
     * @return number
     */
    int missingThrows(int number) throws ThreadDeath { // ok
        return number;
    }

    /**
     * Missing return, but {@inheritDoc} is present.
     *
     * @param number to return
     * @throws java.util.NoSuchElementException sometimes
     */   // ok
    int missingReturnButInheritDocPresent(int number) throws java.util.NoSuchElementException {
        return number;
    }

    /**
     * Missing return in the middle.
     *
     * @param number to return
     * @return
     * @throws java.util.NoSuchElementException sometimes
     */
    private int missingReturnInTheMiddle(int number) { // ok
        return number;
    }

    /**
     * Missing return at the end.
     *
     * @param number to return
     * @return
     */
    private int missingReturnAtTheEnd(int number) { // ok
        return number;
    }

    /**
     * Missing return at the end followed by empty line.
     *
     * @param number to return
     * @return
     *
     */
    private int missingReturnAtTheEndFollowedByEmptyLine(int number) { // ok
        return number;
    }
}
