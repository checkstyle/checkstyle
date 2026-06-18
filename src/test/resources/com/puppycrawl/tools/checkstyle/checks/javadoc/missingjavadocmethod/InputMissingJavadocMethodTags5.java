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
enum InputMissingJavadocMethodTagsEnum5
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

        public void someOtherMethod() // violation 'Missing a Javadoc comment.'
        {

        }
    }
}

@interface InputMissingJavadocMethodTagsAnnotation
{
    String someField(); // violation 'Missing a Javadoc comment.'
    int A_CONSTANT = 0;
    /** Some javadoc. */
    int B_CONSTANT = 1;
    /** @return This tag is valid here and expected with Java 8 */
    String someField2();
    /** {@inheritDoc} */
    String someField3();
}

/* Config:
 * scope = "private"
 */
/**
 * Some javadoc.
 */
public class InputMissingJavadocMethodTags5 {

    /**
     * Constructor.
     */
    public InputMissingJavadocMethodTags5() {
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
    String[] results() default {};
}
class MoreExamples {
    /** @param algorithm*/
    public void setAlgorithm(String algorithm) {}
}
