/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = (default)false
ignoreFinal = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceTryBlock {

    void tryWithUsageInTryBody() {
        int a = 1; // violation 'Distance .* is 4.'
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        try {
            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void tryWithUsageInCatch() {
        int a = 1; // violation 'Distance .* is 4.'
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        try {
            System.lineSeparator();
        } catch (Exception e) {
            System.out.println(a);
        }
    }

    void tryWithUsageInFinally() {
        int a = 1; // violation 'Distance .* is 4.'
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        try {
            System.lineSeparator();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(a);
        }
    }

    // no violation - distance is within allowed limit
    void tryWithinAllowedDistance() {
        int a = 1;
        System.lineSeparator();
        System.lineSeparator();
        try {
            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // no violation - usage immediately in try
    void tryImmediateUsage() {
        int a = 1;
        try {
            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void tryWithoutFinally() {
        int a = 1; // violation 'Distance .* is 4.'
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        try {
            a = 2;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void tryFinallyWithoutCatch() {
        int a = 1; // violation 'Distance .* is 4.'
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        try {
            a = 2;
        } finally {
            System.lineSeparator();
        }
    }

    void synchronizedBlock() {
        int a = 1;
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        synchronized (this) {
            System.out.println(a);
        }
    }
}
