/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

import java.util.List;
import java.util.function.Supplier;

public class InputCommentsIndentationPreviousStatementTwo {

    void oldSwitch(int value) {
        switch (value) {
            case 1:
                // comment under case
                System.out.println(1);
                // fall through
            case 2:
                System.out.println(2);
                break;
            // comment before default
            default:
                // comment in default
        }
    }

    void lambdasAndChains(List<String> list) {
        final Supplier<String> supplier = () -> {
            // comment in lambda body
          // violation '.* incorrect .* level 10, expected is 12, .* same .* as line 34.'
            return "value";
        };
        list.stream()
            // comment in method call chain
            .map(String::trim)
            .forEach(System.out::println);
        final String text = "a"
            // comment in string concatenation
            + "b";
        System.out.println(supplier.get() + text);
    }

    void controlFlow(boolean flag) {
        try {
            // comment in try
            System.out.println(flag);
        }
        // comment between try and catch
        catch (IllegalStateException exception) {
            // comment in catch
            System.out.println(exception);
        }
        if (flag) {
            System.out.println("if");
        }
        // comment between if and else
        else {
            // comment in else
            System.out.println("else");
        }
    }

    enum Color {
        // comment before first constant
        RED {
            // comment in body of constant
            @Override
            String code() {
                return "r";
            }
        },
        // comment between constants
        GREEN;

        String code() {
            // comment in enum method
            return "g";
        }
    }

    interface Shape {
        // comment in interface
        default double area() {
            // comment in default method
            return 0;
        }
    }
}
