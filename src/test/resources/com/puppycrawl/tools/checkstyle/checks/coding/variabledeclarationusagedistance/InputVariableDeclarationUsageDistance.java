/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistance {

    private static int test1 = 0;

    static {
        int b = 0;
        int d = 0;
        {
            d = ++b;
        }
    }

    static {
        int c = 0;
        int a = 3;
        int b = 2;
        {
            a = a + b;
            c = b;
        }
        {
            c--;
        }
        a = 7;
    }

    static {
        int a = -1;
        int b = 2;
        b++;
        int c = --b;
        a = b;
    }

    public InputVariableDeclarationUsageDistance(int test1) {
        int temp = -1;
        this.test1 = test1;
        temp = test1;
    }

    public boolean testMethod() {
        int temp = 7;
        new InputVariableDeclarationUsageDistance(2);
        String.valueOf(temp);
        boolean result = false;
        String str = "";
        if (test1 > 1) {
            str = "123";
            result = true;
        }
        return result;
    }

    public void testMethod2() {
        int count;
        int a = 3;
        int b = 2;
        {
            a = a + b - 5 + 2 * a;
            count = b;
        }
    }

    public void testMethod3() {
        int count; // violation 'Distance between .* declaration and its first usage is 4.'
        int a = 3;
        int b = 3;
        a = a + b;
        b = a + a;
        testMethod2();
        count = b;
    }

    public void testMethod4(int arg) {
        int d = 0;
        for (int i = 0; i < 10; i++) {
            d++;
            if (i > 5) {
                d += arg;
            }
        }

        String[] ar = { "1", "2" };
        for (String st : ar) {
            System.identityHashCode(st);
        }
    }

    public void testMethod5() {
        int arg = 7;
        boolean b = true;
        boolean bb = false;
        if (b) {
            if (!bb) {
                b = false;
            }
        }
        testMethod4(arg);
    }
}
