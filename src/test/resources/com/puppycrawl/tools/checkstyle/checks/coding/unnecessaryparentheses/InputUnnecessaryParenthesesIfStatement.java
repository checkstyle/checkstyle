package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

public class InputUnnecessaryParenthesesIfStatement {

    private void method() {
        int x, y, z;
        x = 0;
        y = 0;

        z = (x < y) ? x : y;

        if ((x < y ) && (x > z)) {
            return;
        }

        if (((x < y ) && (x > z))) {
            return;
        }

        if (!(x <= y ) || (x >= z)) {
            return;
        }

        if ((x == y ) || (x != z)) {
            return;
        }

        if (((x == y ) || (x != z))) {
            return;
        }

        if ((Integer.valueOf(x) instanceof Integer ) || Integer.valueOf(y) instanceof Integer) {
            return;
        }

    }

    private void method(String sectionName) {
        if ("Some content".equals(sectionName) || "Some overview".equals(sectionName)
                // suppression list
                || (!"AbbreviationAsWordInName".equals(sectionName)
                && !"AbstractClassName".equals(sectionName)
        )) {
            return;
        }

        if (sectionName instanceof String && "Other Overview".equals(sectionName)
                // suppression list
                && (!"AbbreviationAsWordInName".equals(sectionName)
                || !"AbstractClassName".equals(sectionName)
        )) {
            return;
        }
    }

    private void method(boolean a, boolean b) {
        if( (a || b) ) {
            return;
        }
    }

}
