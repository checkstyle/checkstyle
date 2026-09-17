/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormattingTextBlockAsArgument {

    void methodWrong() {
        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 3 lines below 'Opening quotes (""") of text-block must be on the new line'
        foo("""
            Argument 1
            """, """
                 Argument 2
                 """);

        // violation 5 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 6 lines below 'Opening quotes (""") of text-block must be on the new line'
        foo(
            """
            Argument 1
            """, """
                 Argument 2
                 """, """
                      Argument 3
                      """);

        // violation 4 lines below 'Opening quotes (""") of text-block must be on the new line'
        String[] a = {
                """
                Argument 1
                ""","""
                    Argument 2
                    """,
        };

        foo(a);
    }

    void methodCorrect() {
        foo(
            """
            Argument 1
            """,
            """
            Argument 2
            """);

        foo(
            """
            Argument 1
            """,
            """
            Argument 2
            """,
            """
            Argument 3
            """);

        String[] a = {
                """
                Argument 1
                """,
                """
                Argument 2
                """,
        };
        foo(a);
    }


    void foo(String... a) {
    }
}
