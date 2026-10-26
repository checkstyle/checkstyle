/*
DuplicateElseIfCondition


*/

package com.puppycrawl.tools.checkstyle.checks.coding.duplicateelseifcondition;

public class InputDuplicateElseIfConditionIfStructure {
    void commentsAndFormattingIgnored(int a, int b) {
        if (a > b) {
        } else if (/* comment */ a   >   b) {
            // violation above 'Duplicate condition, identical to the condition at line '11'.'
        }
    }

    void lineWrapsIgnored(int a, int b) {
        if (a > b) {
        } else if (
            a > // violation 'Duplicate condition, identical to the condition at line '18'.'
            b
        ) {
        }
    }

    void noBraces(int x) {
        if (x > 0);
        else if (x < 0);
        else if (x > 0);
            // violation above 'Duplicate condition, identical to the condition at line '27'.'
    }

    void trailingElseOnly(int x) {
        if (x > 0) {
        } else if (x < 0) {
        } else {
        }
    }

    void singleIfWithoutElse(int x) {
        if (x > 0) {
        }
    }

    void singleIfWithElse(int x) {
        if (x > 0) {
        } else {
        }
    }

    void siblingIfsAreIndependent(int x) {
        if (x > 0) {
        }
        if (x > 0) {
        }
    }

    void siblingIfElseAreIndependent(int x) {
        if (x > 0) {
        } else {
        }
        if (x > 0) {
        } else {
        }
    }
}
