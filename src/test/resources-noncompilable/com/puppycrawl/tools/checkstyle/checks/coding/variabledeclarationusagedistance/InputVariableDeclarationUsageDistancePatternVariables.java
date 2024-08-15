/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistancePatternVariables {

    void m(Object obj) {
        switch (obj) {
            case ColoredPoint(int x, _, _) -> {
                System.out.println("line1");
                System.out.println("line2");
                System.out.println("line3");
                System.out.println("line4");
                System.out.println(x); // ok, 'x' is pattern variable
            }
            default -> { }
        }
        if (obj instanceof ColoredPoint(int a, _, _)) {
            System.out.println("line1");
            System.out.println("line2");
            System.out.println("line3");
            System.out.println("line4");
            System.out.println(a); // ok, 'a' is pattern variable
        }

        int b; // violation, 'Distance between variable 'b'*.'
        System.out.println("line1");
        System.out.println("line2");
        System.out.println("line3");
        System.out.println("line4");
        b = 1;
    }

    record ColoredPoint(int x, int y, int color) {
    }
}
