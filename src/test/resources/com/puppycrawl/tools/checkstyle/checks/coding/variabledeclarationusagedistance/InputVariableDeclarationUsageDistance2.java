/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistance2 {
}


class testClass {
    int l = 12;
    void method2() {
        int s = 12;
        int j = 13;
        hoo k = () -> {
            Integer.valueOf(j);
            hoo obj2 = () -> {
                com.puppycrawl.tools.checkstyle.checks.coding
                        .variabledeclarationusagedistance.testClass
                        obj3 = new com.puppycrawl.tools.checkstyle.checks.coding
                        .variabledeclarationusagedistance.testClass() {
                    void foo() {
                        s += 12;
                    }
                };
                obj3.getClass();
            };
            obj2.anotherMethod();
        };
        k.anotherMethod();
    }

    int s = 2;
}

interface hoo {
    int n = 12;
    void anotherMethod();
}
