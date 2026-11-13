package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionCommentsOnAnnotationsAndEnums2 {
    enum InputJavadocVariableTagsEnum
    {
        CONSTANT_A, // violation

        /**
         *
         */
        CONSTANT_B,

        CONSTANT_C // violation
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
}
