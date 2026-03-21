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

enum InputJavadocMethodTagsEnum
{
    CONSTANT_A,

    /**
     *
     */
    CONSTANT_B,

    CONSTANT_C
    {
        /**
         *
         */
        public void someMethod()
        {
        }

        public void someOtherMethod()
        {

        }
    }
}

@interface InputJavadocMethodTagsAnnotation
{
    String someField();
    int A_CONSTANT = 0;
    /** Some javadoc. */
    int B_CONSTANT = 1;
    /** @return This tag is valid here and expected with Java 8 */
    String someField2();
    /** {@inheritDoc} */
    String someField3(); // violation 'Invalid use of the '@inheritDoc' tag.'
}

/**
 * Some javadoc.
 */
public class InputJavadocMethodTags {

    /**
     * Constructor.
     */
    public InputJavadocMethodTags() {
    }

   /**
    * Sample method.
    * @param arg1   first argument
    * @param arg2   second argument
    * @return java.lang.String      the result string
    * @throws java.lang.Exception   in case of problem
    */
    public final String myMethod(final String arg1,
                                 final Object arg2)
      throws Exception
    {
        return null;
    }
}

/**
 *  Added to make this file compilable.
 */
class WrongException extends RuntimeException
{
}

@interface InputInterfaceTest {
    /** @return
     * nothing
     * @return
     * oops */
    // violation 2 lines above 'Duplicate @return tag.'
    String[] results() default {};
}
class MoreExamples {
    /** @param algorithm*/
    public void setAlgorithm(String algorithm) {}
}
