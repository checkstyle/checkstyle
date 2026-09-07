/*
LeftCurly
option = (default)eol
ignoreEnums = (default)true
tokens = LITERAL_CASE, LITERAL_DEFAULT


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlySwitchWhen {

    void testSwitchRules(Object obj) {
        switch (obj) {
            case ColoredPoint(int x, int y, String c) when (x >= 2) ->
            { // violation ''{' at column 13 should be on the previous line'
                y++;
            }
            case ColoredPoint(int x, int y, String c)
                    when (x == 3) ->
            { // violation ''{' at column 13 should be on the previous line'
                y++;
            }
            case ColoredPoint(int x, int y, String c)
                    when (x == 1)
                    ->
            { // violation ''{' at column 13 should be on the previous line'
                y++;
            }
            case ColoredPoint(int x, int y, String c) when (x == 0) -> {
                y++;
            }
            case Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight)
                    when (upperLeft != null) -> {
                lowerRight.hashCode();
            }
            case String s when (s.isEmpty()) -> { }
            default ->
            { // violation ''{' at column 13 should be on the previous line'
                obj.hashCode();
            }
        }
    }

    void testSwitchStatements(Object obj) {
        switch (obj) {
            case ColoredPoint(int x, int y, String c) when (x >= 2) :
            { } break; // violation ''{' at column 13 should be on the previous line'
            case ColoredPoint(int x, int y, String c)
                    when (x == 1) :
            { } break; // violation ''{' at column 13 should be on the previous line'
            case ColoredPoint(int x, int y, String c)
                    when (x == 0) : { }
                break;
            case String s when (s.isEmpty()) : { }
                break;
            default : { }
        }
    }

    record ColoredPoint(int p, int x, String c) { }

    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) { }
}
