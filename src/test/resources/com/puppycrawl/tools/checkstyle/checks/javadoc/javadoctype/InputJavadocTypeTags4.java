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

public class InputJavadocTypeTags4{

    /**
     * @return something very important.
     * {@inheritDoc}
     */
    int method28(int aParam) {
        return 0;
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

enum InputJavadocTypeTagsEnum
{
    CONSTANT_A,

    /**
     *
     */
    CONSTANT_B,

    CONSTANT_C {
        /**
         *
         */
        public void someMethod() {
        }

        public void someOtherMethod() {

        }
    }
}

@interface InputJavadocTypeTagsAnnotation {
    String someField();

    int A_CONSTANT = 0;
    /**
     * Some javadoc.
     */
    int B_CONSTANT = 1;

    /**
     * @return This tag is valid here and expected with Java 8
     */
    String someField2();
}

/**
 * Some javadoc.
 */
class InputJavadocTypeTags {

    /**
     * Constructor.
     */
    public InputJavadocTypeTags() {
    }

    /**
     * Sample method.
     *
     * @param arg1 first argument
     * @param arg2 second argument
     * @return java.lang.String      the result string
     * @throws java.lang.Exception in case of problem
     */
    public final String myMethod(final String arg1,
                                 final Object arg2)
            throws Exception {
        return null;
    }
}

/**
 * Added to make this file compilable.
 */
class WrongException extends RuntimeException {
}
