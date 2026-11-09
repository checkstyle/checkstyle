/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceTryFinally {

    void test() {
        int a = 0;

        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        for (int i = 0; i < 10; i++) {
            if (true) {
                System.identityHashCode(a);
            }
            else {
                System.identityHashCode(a);
            }
        }

        int b = 0;
        try {
            for (int i = 0; i < 10; i++) {
                if (true) {
                    System.lineSeparator();
                    System.lineSeparator();
                    System.lineSeparator();
                    b = i;
                }
            }

            System.lineSeparator();
            System.lineSeparator();
        }
        catch (Exception e) {
            System.lineSeparator();
        }
        finally {
            System.identityHashCode(b);
        }

        int c = 0;
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();

        if (false) {
        }
        else if (c == 1) {
            if (c != 2) {
                System.lineSeparator();
            }

            System.identityHashCode(c);
        }
        else if (c == 2) {
            System.identityHashCode(c);
        }
    }
}
