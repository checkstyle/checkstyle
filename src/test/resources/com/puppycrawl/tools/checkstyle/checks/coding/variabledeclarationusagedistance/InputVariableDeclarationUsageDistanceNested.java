/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceNested {

    void k() {
        int a = 1;
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        while (true) {
            System.lineSeparator();
            if (true) {
                System.lineSeparator();
            }
            else if (true) {
                System.identityHashCode(a);
            }
            else {
                System.lineSeparator();
            }
        }
    }

    void l() {
        int a = 1;

        while (true) {
            switch (hashCode()) {
                default:
                    break;
            }
            switch (Math.max(1, 2)) {
                case 1:
                    System.identityHashCode(a);
                    break;
                case 2:
                    System.identityHashCode(a);
                    break;
                default:
                    break;
            }
        }
    }

    void tryWithoutFinally() {
        int a = 1; // violation 'Distance between .* declaration and its first usage is 4.'
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        try {
            a = 2;
        }
        catch (Exception e) {
        }
    }

    void m() {
        final int a = 1;
        int b = 0;

        if (b == 1) {
            System.lineSeparator();
        }

        final int c = a + 1;
    }

    private void launch(Integer number) {
        String myInt = (number.toString() + '\0');
        boolean result = false;
        if (number == 123) {
            result = true;
        }
    }

    static int field;

    private void n() {
        long a = 0; // violation 'Distance between .* declaration and its first usage is 4.'

        field = 1;
        field = 2;
        field = 3;
        field = (int) a;
    }
}
