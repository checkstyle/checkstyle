/*
SimplifyBooleanReturn


*/

package com.puppycrawl.tools.checkstyle.checks.coding.simplifybooleanreturn;

public class InputSimplifyBooleanReturnIfWithoutElse {

    boolean ifBlockThenOppositeLiteral(boolean condition) {
        if (condition) { // violation 'Conditional logic can be removed.'
            return true;
        }
        return false;
    }

    boolean ifBlockThenSameLiteral(boolean condition) {
        if (condition) { // violation 'Conditional logic can be removed.'
            return false;
        }
        return false;
    }

    boolean ifSingleStatementThen(boolean condition) {
        if (condition) // violation 'Conditional logic can be removed.'
            return true;
        return false;
    }

    boolean ifNoElseNoFollowingStatement(boolean condition) {
        {
            if (condition) {
                return true;
            }
        }
        return false;
    }

    boolean ifFollowedByNonReturnStatement(boolean condition) {
        if (condition) {
            return true;
        }
        boolean result = condition;
        return result;
    }

    boolean ifFollowedByNonLiteralReturn(boolean condition, boolean other) {
        if (condition) {
            return true;
        }
        return other;
    }

    boolean ifThenReturnsNonLiteral(boolean condition, boolean other) {
        if (condition) {
            return other;
        }
        return false;
    }

    boolean ifThenBlockHasStatementBeforeReturn(boolean condition) {
        if (condition) {
            System.out.println(condition);
            return true;
        }
        return false;
    }

    boolean ifEmptyThenBlock(boolean condition) {
        if (condition) {
        }
        return false;
    }

    boolean nestedIfWithoutBraces(boolean first, boolean second) {
        if (first)
            if (second) {
                return true;
            }
        return false;
    }

    boolean ifInsideLoop(boolean condition) {
        while (condition) {
            if (condition) { // violation 'Conditional logic can be removed.'
                return true;
            }
            return false;
        }
        return true;
    }

    boolean ifInsideCaseGroup(int value, boolean condition) {
        switch (value) {
            case 1:
                if (condition) { // violation 'Conditional logic can be removed.'
                    return true;
                }
                return false;
            default:
                return false;
        }
    }
}
