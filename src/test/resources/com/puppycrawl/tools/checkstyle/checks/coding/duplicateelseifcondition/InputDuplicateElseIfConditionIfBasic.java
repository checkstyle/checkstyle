/*
DuplicateElseIfCondition


*/

package com.puppycrawl.tools.checkstyle.checks.coding.duplicateelseifcondition;

public class InputDuplicateElseIfConditionIfBasic {
    void simpleIfChain(int x, int y, int z) {
        if (x == y) {
        } else if (x == z) {
        } else if (x == y) {
            // violation above 'Duplicate condition, identical to the condition at line '11'.'
        }
    }

    void multipleDuplicates(int a) {
        if (a > 10) {
        } else if (a < 0) {
        } else if (a > 10) {
            // violation above 'Duplicate condition, identical to the condition at line '19'.'
        } else if (a > 10) {
            // violation above 'Duplicate condition, identical to the condition at line '19'.'
        }
    }

    void tripleChainWithDifferentBranches(int value) {
        if (value == 1) {
        } else if (value == 2) {
        } else if (value == 3) {
        } else if (value == 2) {
            // violation above 'Duplicate condition, identical to the condition at line '30'.'
        }
    }

    void duplicatemethodConditionAtEnd(int x) {
        if (x == 0) {
        } else if (x == 1) {
        } else if (x == 2) {
        } else if (x == 0) {
            // violation above 'Duplicate condition, identical to the condition at line '38'.'
        } else {
        }
    }

    void booleanLiterals(boolean flag) {
        if (flag) {
        } else if (flag) {
            // violation above 'Duplicate condition, identical to the condition at line '48'.'
        }
    }

    void methodCalls(String str) {
        if (str.isEmpty()) {
        } else if (str.length() > 5) {
        } else if (str.isEmpty()) {
            // violation above 'Duplicate condition, identical to the condition at line '55'.
        }
    }
}
