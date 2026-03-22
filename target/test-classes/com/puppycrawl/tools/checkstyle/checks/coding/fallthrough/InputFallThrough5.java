/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough5 {

    public Object visit(Object node, Void data) {
        Object operator = null;


        Object operand = node.toString();
        Object operandValue = operand.toString();
        if (operandValue == null) {
            return null;
        }

        switch (operator.toString()) {
        case "122":
            return unaryPromotion(operandValue);
        case "99": {
            Number promoted = unaryPromotion(operandValue);
            if (promoted == null) {
                return null; // single line comment test for slist
            } else if (promoted instanceof Integer) {
                return -promoted.intValue();
            } else {
                assert promoted instanceof Double;
                return -promoted.doubleValue();
            }
        }
        case "89": {
            Number promoted = unaryPromotion(operandValue);
            if (promoted instanceof Integer) {
                return ~promoted.intValue();
            } else if (promoted instanceof Long) {
                return ~promoted.longValue();
            } else {
                return null; /* block comment test for slist */
            }
        }
        case "NEGATION": {
            return booleanInvert(operandValue);
        }
        case "19": {
            Number promoted = unaryPromotion(operandValue);
            if (promoted == null) {
                return null; // single line comment test for slist
                // comment
                /* comment */
            }
            else {
                assert promoted instanceof Double;
                return -promoted.doubleValue();
            }
        }
        case "39": {
            Number promoted = unaryPromotion(operandValue);
            if (promoted instanceof Integer) {
                return ~promoted.intValue();
            }else {
                return null; /* block comment test for slist */ // comment
            }
        }
        default: // increment ops
            throw new AssertionError("unreachable");
        }
    }

    private Object booleanInvert(Object operandValue) {
        return operandValue;
    }

    private Number unaryPromotion(Object operandValue) {
        return null;
    }
}

class Test {
     void method6() {
        int i=0;
        switch (i) {
            case 0:
                break;
            case 1: // random
                // fall through
                i++;
                // i am comment
            case 2: // violation 'Fall\ through from previous branch of the switch statement'
                break;
        }
     }
}
