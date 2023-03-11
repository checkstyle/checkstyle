/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = (default)false
ignoreFinal = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceLabels {
    public void method() {
        boolean eol = false;

        nothing();
        nothing();
        nothing();
        nothing();
        myLoop: // ok
        for (int i = 0; i < 5; i++) {
            if (i == 5) {
                eol = true;
                break myLoop;
            }
        }
    }

    public void nothing() {
    }

    void methodTry() {
        String a = "";
        String b = "abc";
        nothing();
        nothing();
        try (AutoCloseable i = new java.io.StringReader(a)) {
            b.replace(a.charAt(0),'b');
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
