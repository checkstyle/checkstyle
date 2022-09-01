/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceCheckSwitchExpressions2 {
    int switchesInExpressions() {
        int day = 3; // violation 'Distance between variable 'day' .*first usage is 2,.* allowed 1.'
        System.out.println(
                switch (day) {
                    case 1 -> "Monday";
                    case 4 -> {
                        equals1("something");
                        day++;
                        yield "Thursday";
                    }
                    default -> throw new IllegalStateException("Invalid day: " + day);
                }
        );

        int sw = 5; // violation 'Distance between variable 'sw' .*first usage is 2,.* allowed 1.'
        return switch (day) {
            case 0 -> {
                day++;
                yield sw +day;
            }
        };
    }
}


