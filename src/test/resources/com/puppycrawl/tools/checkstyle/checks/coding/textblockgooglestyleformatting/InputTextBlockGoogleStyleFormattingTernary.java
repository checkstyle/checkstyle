/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormattingTernary {

    // Simple ternary with text blocks on same line
    String simpleQuestion = condition ? """
            question branch""" : """
            colon branch""";
    // violation above 'Opening quotes (""") of text-block must be on the new line'
    // violation above 'Opening quotes (""") of text-block must be on the new line'

    // Ternary with proper formatting
    String properTernary = condition
            ? """
            question branch
            """
            : """
            colon branch
            """;

    // Nested ternary operators
    String nestedTernary = condition1
            ? """
            first branch
            """
            : condition2
            ? """
            second branch"""  // violation 'Opening quotes (""") of text-block must be on the new line'
            : """
            third branch
            """;

    // Ternary in method call
    void methodWithTernary() {
        String result = method(
            condition ? """
                    yes branch""" : """
                    no branch"""
        );
        // violation 'Opening quotes (""") of text-block must be on the new line'
        // violation 'Opening quotes (""") of text-block must be on the new line'
    }

    // Assignment with cascading ternary
    String cascading = flag1 ? """
            value1""" : flag2 ? """
            value2""" : """
            value3""";
    // violation above 'Opening quotes (""") of text-block must be on the new line'
    // violation above 'Opening quotes (""") of text-block must be on the new line'
    // violation above 'Opening quotes (""") of text-block must be on the new line'

    // Complex expression with ternary
    String complex = (a + b > c)
            ? """
            high value
            """
            : """
            low value
            """;

    // Ternary with text block containing special chars
    String specialChars = valid ? """
            valid input: $%^&*()""" : """
            invalid input""";
    // violation above 'Opening quotes (""") of text-block must be on the new line'
    // violation above 'Opening quotes (""") of text-block must be on the new line'

    // Multiple nested ternaries
    String multiNested = a ? """
            a true""" : b ? """
            b true""" : c ? """
            c true""" : """
            all false""";
    // violation above 'Opening quotes (""") of text-block must be on the new line' (x4)

    // Ternary in array initializer
    String[] array = {
        condition1 ? """
                item1""" : """
                default1""",
        // violations above
        condition2 ? """
                item2""" : """
                default2"""
        // violations above
    };

    // Ternary in list initializer
    java.util.List<String> list = java.util.Arrays.asList(
        condition ? """
                element""" : """
                fallback"""
    );
    // violation above 'Opening quotes (""") of text-block must be on the new line'
    // violation above 'Opening quotes (""") of text-block must be on the new line'

    // Ternary with line breaks before question/colon
    String withLineBreaks = condition
            ? """
            proper question branch
            """
            : """
            proper colon branch
            """;

    // Question operator with text block - vertical alignment issue
    String verticalAlignmentIssue = condition ? """
            not aligned"""
            : """
            also not aligned""";
    // violation above 'Opening quotes (""") of text-block must be on the new line'
    // 2 violations above 'Text-block quotes are not vertically aligned'

    // Colon operator with text block - vertical alignment issue
    String colonAlignment = condition ? """
            question branch
            """
            : """
                colon branch""";  // violation 'Text-block quotes are not vertically aligned'

    // Question with proper formatting
    String properQuestion = check
            ? """
            question branch with text block
            """
            : """
            colon branch with text block
            """;

    // Ternary as method argument
    void testMethodArg() {
        processData(flag ? """
                enabled""" : """
                disabled""");
        // violation above 'Opening quotes (""") of text-block must be on the new line' (x2)
    }

    // Ternary in return statement
    String getContent() {
        return isValid ? """
                valid content""" : """
                error message""";
        // violation above 'Opening quotes (""") of text-block must be on the new line'
        // violation above 'Opening quotes (""") of text-block must be on the new line'
    }

    // Ternary with wrapped conditions
    String wrappedCondition = (
            veryLongConditionName1 &&
            veryLongConditionName2 &&
            veryLongConditionName3
    )
            ? """
            complex condition true
            """
            : """
            complex condition false
            """;

    // Multiple ternaries in single line - all improper
    String multiLine = a ? """
            a""" : b ? """
            b""" : c ? """
            c""" : """
            d""";
    // multiple violations above

    // Ternary with method calls in condition
    String withMethodCall = getValue().isEmpty() ? """
            empty""" : """
            not empty""";
    // violation above 'Opening quotes (""") of text-block must be on the new line'
    // violation above 'Opening quotes (""") of text-block must be on the new line'

    // Lambda with ternary text blocks
    java.util.function.Function<Boolean, String> lambda = b -> b ? """
            lambda true""" : """
            lambda false""";
    // violation above 'Opening quotes (""") of text-block must be on the new line'
    // violation above 'Opening quotes (""") of text-block must be on the new line'

    // Edge case: deeply nested ternaries with text blocks
    String deepNesting = x > 0 ? """
            positive""" : x < 0 ? """
            negative""" : """
            zero""";
    // violation above 'Opening quotes (""") of text-block must be on the new line' (x3)
}
