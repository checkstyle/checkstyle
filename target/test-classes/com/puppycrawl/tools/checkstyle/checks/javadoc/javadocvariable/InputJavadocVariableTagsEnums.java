/*
JavadocVariable
accessModifiers = (default)public,protected,package,private
ignoreNamePattern = (default)null
tokens = (default)ENUM_CONSTANT_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

import java.io.IOException;

enum InputJavadocVariableTagsEnum
{
    CONSTANT_A, // violation, 'Missing a Javadoc comment

    /**
     *
     */
    CONSTANT_B,

    CONSTANT_C // violation, 'Missing a Javadoc comment'
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

@interface InputJavadocVariableTagsAnnotation
{
    String someField();
    int A_CONSTANT = 0; // violation, 'Missing a Javadoc comment'
    /** Some javadoc. */
    int B_CONSTANT = 1;
    /** @return This tag is valid here and expected with Java 8 */
    String someField2();
}

/**
 * Some javadoc.
 */
public class InputJavadocVariableTagsEnums {

    /**
     * Constructor.
     */
    public InputJavadocVariableTagsEnums() {
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

