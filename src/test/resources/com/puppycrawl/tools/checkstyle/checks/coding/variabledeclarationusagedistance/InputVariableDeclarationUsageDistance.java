/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistance {

    public void testMethod3() {
        int count; // violation 'Distance .* is 4.'
        int a = 3;
        int b = 3;
        a = a + b;
        b = a + a;
        System.lineSeparator();
        count = b;
    }

    public void testMethod14() {
        Object t = new Object(); // violation 'Distance .* is 5.'
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        t.toString();
    }

    public void testIssue32_9() {
        Object myOption = null; // violation 'Distance .* is 7.'
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        myOption.toString();
    }

    public void testIssue32_10() {
        Object myOption = null; // violation 'Distance .* is 6.'
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        myOption.toString();
    }

    public void testIssue32_11() {
        int count = 0; // violation 'Distance .* is 4.'
        String[] files = {};
        System.identityHashCode("Data archival started");
        files.notify();
        System.identityHashCode("sss");
        count++;
    }

    void f() {
        int a = 1; // violation 'Distance .* is 4.'
        System.lineSeparator(); System.lineSeparator();
        System.lineSeparator(); System.lineSeparator();
        while (true)
            System.identityHashCode(a);
    }

    void h() {
        int a = 1; // violation 'Distance .* is 4.'
        System.lineSeparator(); System.lineSeparator();
        System.lineSeparator(); System.lineSeparator();
        while (true)
            while (true)
                a++;
    }

    void tryWithoutFinally() {
        int a = 1; // violation 'Distance .* is 4.'
        System.lineSeparator(); System.lineSeparator();
        System.lineSeparator();
        try {
            a = 2;
        }
        catch (Exception e) {}
    }

    static int field;

    private void n() {
        long a = 0; // violation 'Distance .* is 4.'
        field = 1;
        field = 2;
        field = 3;
        field = (int) a;
    }
}
