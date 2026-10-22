/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

// comment between package and class
public class InputCommentsIndentationPreviousStatementOne {

    // comment as first member
    private final int[] numbers = {
        // comment as first element of array initializer
        1,
        2,
        // comment between elements of array initializer
        3,
            // violation '.* incorrect .* level 12, expected is 8, .* same .* as line 21.'
        4,
        // comment at the end of array initializer
    };

    private final int[][] matrix = {
        {
            // comment in nested array initializer
            1,
        },
        // comment between nested array initializers
        {2},
    };

    static {
        // comment in static initializer
        System.out.println("static");

        // comment after empty line in static initializer
    }

    {
        // comment in instance initializer
    }

    /* block comment before method */
    void anonymousClass() {
        final Runnable runnable = new Runnable() {
            // comment as first member of anonymous class
            @Override
            public void run() {
                // comment in method of anonymous class
                System.out.println("run");
            }
        // violation '.* incorrect .* level 8, expected is 12, .* same .* as line 49.'
            // comment as last member of anonymous class
        };
        runnable.run();
        // comment after anonymous class


        /*
         * multi-line block comment after empty lines
         */
        runnable.run();
    }

    int switchExpression(Object value) {
        final int result = switch (value) {
            // comment before first rule
            case Integer integer when integer > 0 -> {
                // comment in rule block
                yield integer;
            }
            case String string -> {
                final int length = string.length();
                // comment before yield
                yield length;
            }
            // comment before default rule
            default -> 0;
        };
        if (value instanceof Integer integer && integer > 1) {
            // comment in block of pattern instanceof
            return integer;
        }
        // comment before return
        return result;
    }

    // comment as last member
}
// comment after class
