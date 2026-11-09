/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceLoops {

    void a() {
        int a = 1;
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        while (true) {
            System.lineSeparator();
            System.identityHashCode(a);
        }
    }

    void b() {
        int a = 1;
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        do {
            System.lineSeparator();
            System.identityHashCode(a);
        } while (true);
    }

    void c() {
        int a = 1;
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        for (;;) {
            System.lineSeparator();
            System.identityHashCode(a);
        }
    }

    void d() {
        int a = 1;
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        for (int i : new int[]{1, 2, 3}) {
            System.lineSeparator();
            System.identityHashCode(a);
        }
    }

    void f() {
        int a = 1; // violation 'Distance between .* declaration and its first usage is 4.'
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        while (true)
            System.identityHashCode(a);
    }

    void h() {
        int a = 1; // violation 'Distance between .* declaration and its first usage is 4.'
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        while (true)
            while (true)
                a++;
    }

    void i() {
        int a = 1;
        switch (Math.max(1, 2)) {
            case 1:
                System.lineSeparator();
                break;
            case 2:
                System.lineSeparator();
                break;
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
