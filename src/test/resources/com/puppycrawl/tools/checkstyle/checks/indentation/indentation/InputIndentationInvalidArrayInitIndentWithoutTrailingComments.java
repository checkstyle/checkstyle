package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

/**
 * This test-input is intended to be checked using following configuration:
 *
 * arrayInitIndent = 4
 * basicOffset = 4
 * braceAdjustment = 0
 * caseIndent = 4
 * forceStrictCondition = (default) false
 * lineWrappingIndentation = 4
 * tabWidth = 4
 * throwsIndent = 4
 *
 *
 */
public class InputIndentationInvalidArrayInitIndentWithoutTrailingComments {

    int[] array1 = new int[]
    {

    };
    interface SomeInterface1 {
        @interface SomeAnnotation1 {
            String[] values();
            String[] description() default "";
            String[] description2() default { "hello",
            "checkstyle"};
        }
        @SomeInterface1.SomeAnnotation1(values =
            {
              "hello",
            "checkstyle"
            },
            description = { "hello",
              "checkstyle"
        },
            description2 = {
            "hello", "chekstyle"
            }
        )
      void worked();
    }
}
