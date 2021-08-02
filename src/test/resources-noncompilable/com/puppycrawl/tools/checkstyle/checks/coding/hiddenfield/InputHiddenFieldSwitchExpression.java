/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, PATTERN_VARIABLE_DEF, LAMBDA, RECORD_COMPONENT_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

import java.util.stream.Stream;

public class InputHiddenFieldSwitchExpression {
    private int x;
    private int y;
    private int z;
    private int a;
    private int b;
    String word;
    String otherWord;

    int howMany1(int k, String string) {
        int x = 2; // violation
        switch (k) {
            case 1:
                Stream.of(string.split(" "))
                        .map(word -> word.trim()) // violation
                        .anyMatch(otherWord -> "in".equals(otherWord)); // violation
                int y = x + 1; // violation
                x = y;
                break;
            case 2:
                int z = x + 2; // violation
                x = z;
                break;
            case 3:
                int a = x + 3; // violation
                x = a;
                break;
            default:
                int b = x + 4; // violation
                x = b;
        }
        return x;
    }

    int howMany2(int k, String string) {
        int x = 2; // violation
        return switch (k) {
            case 1 -> {
                Stream.of(string.split(" "))
                        .map(word -> word.trim()) // violation
                        .anyMatch(otherWord -> "in".equals(otherWord)); // violation
                int y = x + 1; // violation
                yield y;
            }
            case 2 -> {
                int z = x + 2; // violation
                yield z;
            }
            case 3 -> {
                int a = x + 3; // violation
                yield a;
            }
            default -> {
                int b = x + 4; // violation
                yield b;
            }
        };
    }
}
