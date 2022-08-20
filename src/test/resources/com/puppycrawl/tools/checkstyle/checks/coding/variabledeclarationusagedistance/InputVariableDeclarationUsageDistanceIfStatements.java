/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.util.HashSet;
import java.util.Set;

public class InputVariableDeclarationUsageDistanceIfStatements {
    void method2() {
        int a = 12; // violation
        if (true) {
            method2();
            checkIfStatementWithoutParen();
            method2();
            a++;
        }
    }

    void checkIfStatementWithoutParen() {
        int a = 12; // violation
        method2();
        if (true)
            a++;
        int b = 12; // violation
        method2();
        if (false)
            method2();
        else if(true)
            b++;
        int c = 12; // violation
        method2();
        checkIfStatementWithoutParen();
        if (true)
            c++;
        else
            method2();
    }

    void testConsecutiveIfStatements() {
        int a = 12; // ok
        int b = 13; // violation
        int c = 14; // violation
        int d = 15; // violation
        if (true)
            a++;
        if (true)
            b++;
        if (false)
            c++;
        if (true)
            d++;
    }

    int testReturnStatement() {
        int a = 1; // violation
        testConsecutiveIfStatements();
        testConsecutiveIfStatements();
        testConsecutiveIfStatements();
        if (true)
            return a;

        return 0;
    }
}
