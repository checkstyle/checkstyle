/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, PATTERN_VARIABLE_DEF, LAMBDA, RECORD_COMPONENT_DEF


*/

// Java17
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
        int x = 2; // violation, ''x' hides a field'
        switch (k) {
            case 1:
                Stream.of(string.split(" "))
                        .map(word -> word.trim()) // violation, ''word' hides a field'
                        .anyMatch(otherWord -> "in".equals(otherWord));
                        // violation above, ''otherWord' hides a field'
                int y = x + 1; // violation, ''y' hides a field'
                x = y;
                break;
            case 2:
                int z = x + 2; // violation, ''z' hides a field'
                x = z;
                break;
            case 3:
                int a = x + 3; // violation, ''a' hides a field'
                x = a;
                break;
            default:
                int b = x + 4; // violation, ''b' hides a field'
                x = b;
        }
        return x;
    }

    int howMany2(int k, String string) {
        int x = 2; // violation, ''x' hides a field'
        return switch (k) {
            case 1 -> {
                Stream.of(string.split(" "))
                        .map(word -> word.trim()) // violation, ''word' hides a field'
                        .anyMatch(otherWord -> "in".equals(otherWord));
                        // violation above, ''otherWord' hides a field'
                int y = x + 1; // violation, ''y' hides a field'
                yield y;
            }
            case 2 -> {
                int z = x + 2; // violation, ''z' hides a field'
                yield z;
            }
            case 3 -> {
                int a = x + 3; // violation, ''a' hides a field'
                yield a;
            }
            default -> {
                int b = x + 4; // violation, ''b' hides a field'
                yield b;
            }
        };
    }
}
