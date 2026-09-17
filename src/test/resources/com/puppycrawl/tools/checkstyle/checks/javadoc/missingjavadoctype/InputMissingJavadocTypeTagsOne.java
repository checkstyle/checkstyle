/*
MissingJavadocType
excludeScope = (default)null
scope = PRIVATE
skipAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;
import java.io.IOException;
// Tests for Javadoc tags.
// violation below 'Missing a Javadoc comment.'
class InputMissingJavadocTypeTags1One
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
         * Bug 841942, "ArrayIndexOutOfBounds in Javadoc handling".
         * @param aParam there is no such param in the method.
         * The problem should be reported with correct line number.
         */

        void method25()
        {
        }
}

// violation below 'Missing a Javadoc comment.'
enum InputJavadocTypeTagsEnum
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

// violation below 'Missing a Javadoc comment.'
@interface InputJavadocTypeTagsAnnotation
{
    String someField();
    int A_CONSTANT = 0;
    /** Some javadoc. */
    int B_CONSTANT = 1;
    /** @return This tag is valid here and expected with Java 8 */
    String someField2();
}

/**
 * Some javadoc.
 */
public class InputMissingJavadocTypeTagsOne {

    /**
     * Constructor.
     */
    public InputMissingJavadocTypeTagsOne() {
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
