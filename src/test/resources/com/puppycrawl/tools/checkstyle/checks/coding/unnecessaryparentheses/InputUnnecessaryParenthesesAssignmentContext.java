/*
UnnecessaryParentheses
tokens = EXPR, ASSIGN


*/
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

import java.util.function.Consumer;

public class InputUnnecessaryParenthesesAssignmentContext {
    Object anonymous = new Object() {
        void method(Object value) {
            consume((value == null ? value : null)); // violation 'around expression'
            Object assigned = consume((value == null ? value : null));
            // violation above 'around assignment right-hand side'
        }
    };

    Consumer<Object> expressionLambda = value -> consume((value == null ? value : null));
    // violation above 'around expression'

    Consumer<Object> blockLambda = value -> {
        consume((value == null ? value : null)); // violation 'around expression'
        Object assigned = consume((value == null ? value : null));
        // violation above 'around assignment right-hand side'
    };

    Object ordinaryAssignment = consume((anonymous == null ? anonymous : null));
    // violation above 'around assignment right-hand side'

    static Object consume(Object value) {
        return value;
    }
}
