/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = LITERAL_SWITCH, LITERAL_IF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestSwitchExpression3 {
    int foo(int yield) {
        return switch (yield) {
            case 1 -> 2;
            case 2 -> 3;
            case 3 -> 4;
            default -> 5;
        };
    }

    int foo2(int yield) {
        return switch (yield) {
            case 1 -> 2;
            case 2 -> 3;
            case 3 -> 4;
            default -> 5;
        }; }
}
