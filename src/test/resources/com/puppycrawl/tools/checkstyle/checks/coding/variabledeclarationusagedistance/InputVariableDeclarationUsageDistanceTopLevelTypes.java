/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = (default)false
ignoreFinal = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public enum InputVariableDeclarationUsageDistanceTopLevelTypes {
    INSTANCE;

    {
        int a = 1;
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(a);
    }

    void method() {
        int b = 1;
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(b);
    }

    void methodWithViolation() {
        int f = 1; // violation 'Distance .* is 4.'
        first();
        second();
        third();
        System.out.println(f);
    }

    void first() {
    }

    void second() {
    }

    void third() {
    }
}

record InputVariableDeclarationUsageDistanceTopLevelTypesRecord() {
    void method() {
        int c = 1;
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(c);
    }
}

interface InputVariableDeclarationUsageDistanceTopLevelTypesInterface {
    default void method() {
        int d = 1;
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(d);
    }
}

class InputVariableDeclarationUsageDistanceTopLevelTypesClass {
    void method() {
        int e = 1;
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(e);
    }
}
