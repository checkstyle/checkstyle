//Checks
//
//onfiguration com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck
//
//
package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormattingTernary {

    public void testSimpleTernaryOperatorQuestionMark() {
        String s = "test";
        // violation below 'Opening quotes (""") of text-block must be on the new line'
        String result = s.isEmpty() ? """
                true""" : """
                false""";
    }

    public void testSimpleTernaryOperatorQuestion() {
        String s = "test";
        // violation below 'Opening quotes (""") of text-block must be on the new line'
        String result = true ? """
                value""" : """
                other""";
    }

    public void testTernaryOperatorProperFormatQuestion() {
        String s = "test";
        // Properly formatted - question on own line
        String result = s.isEmpty() ?
            """
            value1
            """
            : """
            value2
            """;
    }

    public void testTernaryOperatorProperFormatColon() {
        String s = "test";
        // Properly formatted - colon on own line
        String result = s.isEmpty() ? """
            value1
            """
            :
            """
            value2
            """;
    }

    public void testNestedTernaryOperators() {
        String s = "test";
        int x = 5;
        // Nested ternary with text blocks
        // 2 violations below 'Opening quotes (""") of text-block must be on the new line'
        String result = s.isEmpty() ? """
                nested1""" : x > 0 ? """
                nested2""" : """
                nested3""";
    }

    public void testNestedTernaryProperFormat() {
        String s = "test";
        int x = 5;
        // Properly formatted nested ternary
        String result = s.isEmpty() ?
            """
            value1
            """
            : x > 0 ?
            """
            value2
            """
            :
            """
            value3
            """;
    }

    public void testTernaryInMethodCall() {
        String s = "test";
        // 2 violations below 'Opening quotes (""") of text-block must be on the new line'
        someMethod(s.isEmpty() ? """
                param1""" : """
                param2""");
    }

    public void testTernaryInMethodCallProper() {
        String s = "test";
        // Properly formatted
        someMethod(
            s.isEmpty() ?
                """
                param1
                """
                :
                """
                param2
                """
        );
    }

    public void testTernaryWithAssignmentExpression() {
        String s = "test";
        // 3 violations below 'Opening quotes (""") of text-block must be on the new line'
        String x = s.equals("a") ? """
                caseA""" : s.equals("b") ? """
                caseB""" : """
                default""";
    }

    public void testTernaryWithAssignmentProper() {
        String s = "test";
        String x = s.equals("a") ?
            """
            caseA
            """
            : s.equals("b") ?
            """
            caseB
            """
            :
            """
            default
            """;
    }

    public void testTernaryOperatorWithComplexCondition() {
        String s = "test";
        int length = 10;
        // 2 violations below 'Opening quotes (""") of text-block must be on the new line'
        String result = s.isEmpty() && length > 5 ? """
                complex1""" : """
                complex2""";
    }

    public void testTernaryOperatorWithComplexConditionProper() {
        String s = "test";
        int length = 10;
        String result = s.isEmpty() && length > 5 ?
            """
            complex1
            """
            :
            """
            complex2
            """;
    }

    public void testTernaryMixedWithRegularQuotesQuestion() {
        String s = "test";
        // violation below 'Opening quotes (""") of text-block must be on the new line'
        String result = s.isEmpty() ? """
                textblock""" : "regular";
    }

    public void testTernaryMixedWithRegularQuotes() {
        String s = "test";
        String result = s.isEmpty() ?
            """
            textblock
            """
            : "regular";
    }

    public void testTernaryMixedColon() {
        String s = "test";
        String result = s.isEmpty() ? "regular" :
            """
            textblock
            """;
    }

    public void testMultipleTernaryOperatorsQuestion() {
        String s = "test";
        String t = "other";
        // 3 violations below 'Opening quotes (""") of text-block must be on the new line'
        String result = s.isEmpty() ? """
                first""" : t.isEmpty() ? """
                second""" : """
                third""";
    }

    public void testMultipleTernaryOperatorsProper() {
        String s = "test";
        String t = "other";
        String result = s.isEmpty() ?
            """
            first
            """
            : t.isEmpty() ?
            """
            second
            """
            :
            """
            third
            """;
    }

    public void testTernaryInArrayInitializer() {
        String s = "test";
        // 2 violations below 'Opening quotes (""") of text-block must be on the new line'
        String[] arr = {s.isEmpty() ? """
                elem1""" : """
                elem2"""};
    }

    public void testTernaryInArrayInitializerProper() {
        String s = "test";
        String[] arr = {
            s.isEmpty() ?
                """
                elem1
                """
                :
                """
                elem2
                """
        };
    }

    public void testTernaryWithComplexContentQuestion() {
        String s = "test";
        // 2 violations below 'Opening quotes (""") of text-block must be on the new line'
        String result = s.isEmpty() ? """
                Line 1
                Line 2
                Line 3""" : """
                Other 1
                Other 2""";
    }

    public void testTernaryWithComplexContentProper() {
        String s = "test";
        String result = s.isEmpty() ?
            """
            Line 1
            Line 2
            Line 3
            """
            :
            """
            Other 1
            Other 2
            """;
    }

    public void testTernaryInConditionalExpression() {
        String s = "test";
        if (true) {
            // 2 violations below 'Opening quotes (""") of text-block must be on the new line'
            String result = s.isEmpty() ? """
                    ifTrue""" : """
                    ifFalse""";
        }
    }

    public void testTernaryInConditionalExpressionProper() {
        String s = "test";
        if (true) {
            String result = s.isEmpty() ?
                """
                ifTrue
                """
                :
                """
                ifFalse
                """;
        }
    }

    public void testTernaryQuestionWithoutNewlineAndColon() {
        String s = "test";
        // violation below 'Opening quotes (""") of text-block must be on the new line'
        String result = s.isEmpty() ? """
                question""" :
            """
            colon
            """;
    }

    public void testColonOperatorAlreadyWorking() {
        String s = "test";
        String result = s.isEmpty() ? "regular" :
            """
            colon
            """;
    }

    // Helper method
    private void someMethod(String param) {
    }
}
