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
        int a = 12; // violation 'Distance between .* declaration and its first usage is 4.'
        if (true) {
            method2();
            checkIfStatementWithoutParen();
            method2();
            a++;
        }
    }

    void checkIfStatementWithoutParen() {
        int a = 12; // violation 'Distance between .* declaration and its first usage is 2.'
        method2();
        if (true)
            a++;
        int b = 12; // violation 'Distance between .* declaration and its first usage is 2.'
        method2();
        if (false)
            method2();
        else if(true)
            b++;
        int c = 12; // violation 'Distance between .* declaration and its first usage is 3.'
        method2();
        checkIfStatementWithoutParen();
        if (true)
            c++;
        else
            method2();
    }

    void testConsecutiveIfStatements() {
        int a = 12; // ok
        int b = 13; // violation 'Distance between .* declaration and its first usage is 2.'
        int c = 14; // violation 'Distance between .* declaration and its first usage is 3.'
        int d = 15; // violation 'Distance between .* declaration and its first usage is 4.'
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
        int a = 1; // violation 'Distance between .* declaration and its first usage is 4.'
        testConsecutiveIfStatements();
        testConsecutiveIfStatements();
        testConsecutiveIfStatements();
        if (true)
            return a;

        return 0;
    }
}
