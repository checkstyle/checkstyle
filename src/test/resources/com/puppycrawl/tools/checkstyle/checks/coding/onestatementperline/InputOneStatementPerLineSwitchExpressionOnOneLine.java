/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import java.util.function.IntUnaryOperator;

public class InputOneStatementPerLineSwitchExpressionOnOneLine {
    int field;

    int returned(int k) {
        return switch (k) { case 1 -> 10; default -> 20; };
    }

    int assigned(int k) {
        int y = switch (k) { case 1 -> 10; case 2 -> 20; default -> 30; };
        return y;
    }

    int argument(int k) {
        return Math.abs(switch (k) { case 1 -> -1; default -> 1; });
    }

    IntUnaryOperator lambda() {
        return k -> switch (k) { case 1 -> 10; default -> 20; };
    }

    void secondStatement(int k) {
        int a = switch (k) { case 1 -> 10; default -> 20; }; int b = a;
        // violation above 'Only one statement per line allowed.'
    }

    void switchStatement(int k) {
        switch (k) { case 1 -> field = 2; default -> field = 3; }
        // violation above 'Only one statement per line allowed.'
    }
}
