/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.util.*;

public class InputVariableDeclarationUsageDistanceGeneral2 {
    private void o() {
        boolean first = true; // violation
        do {
            System.lineSeparator();
            System.lineSeparator();
            System.lineSeparator();
            System.lineSeparator();
            System.lineSeparator();
            if (first) {}
        } while(true);
    }

    private static void checkInvariants() {
        Set<Integer> allInvariants = new HashSet<Integer>(); // violation
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    System.lineSeparator();

                    allInvariants.add(k);
                }
            }
    }

    private void p() {
        float wet_delta = 0;

        if (wet_delta != 0) {
            for (int i = 0; i < 10; i++) {
                System.lineSeparator();
                System.lineSeparator();
                System.lineSeparator();
                System.lineSeparator();
                float wet = 0;
                wet += wet_delta;
            }
        } else if (false) {
        } else {
        }
    }
}
