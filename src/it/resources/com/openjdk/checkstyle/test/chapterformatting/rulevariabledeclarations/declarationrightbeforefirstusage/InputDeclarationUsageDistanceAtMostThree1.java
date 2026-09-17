package com.openjdk.checkstyle.test.chapterformatting.rulevariabledeclarations.declarationrightbeforefirstusage;

// violation first line 'Header mismatch'

/** Some javadoc. */
public class InputDeclarationUsageDistanceAtMostThree1 {

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
        a = b; // DECLARATION OF VARIABLE 'a' SHOULD BE HERE (distance = 2)
    }

    /** Some javadoc. */
    public InputDeclarationUsageDistanceAtMostThree1(int test2) {
        int temp = -1;
        this.test1 = test2;
        temp = test2; // DECLARATION OF VARIABLE 'temp' SHOULD BE HERE (distance = 2)
    }

    /** Some javadoc. */
    public boolean testMethod() {
        int temp = 7;
        new InputDeclarationUsageDistanceAtMostThree1(2);
        String.valueOf(temp); // DECLARATION OF VARIABLE 'temp' SHOULD BE HERE (distance = 2)
        boolean result = false;
        String str = "";
        if (test1 > 1) {
            str = "123";
            result = true;
        }
        return result;
    }

    /** Some javadoc. */
    public void testMethod2() {
        int count;
        int a = 3;
        int b = 2;
        {
            a = a + b - 5 + 2 * a;
            count = b; // DECLARATION OF VARIABLE 'count' SHOULD BE HERE (distance = 2)
        }
    }

    /** Some javadoc. */
    public void testMethod3() {
        int count;
        // violation above 'Distance between variable 'count' .* first usage is 4, but allowed 3.*'
        int a = 3;
        int b = 3;
        a = a + b;
        b = a + a;
        testMethod2();
        count = b; // DECLARATION OF VARIABLE 'count' SHOULD BE HERE (distance = 4)
    }

    /** Some javadoc. */
    public void testMethod4(int arg) {
        int d = 0;
        for (int i = 0; i < 10; i++) {
            d++;
            if (i > 5) {
                d += arg;
            }
        }

        String[] ar = {"1", "2"};
        for (String st : ar) {
            System.identityHashCode(st);
        }
    }

    /** Some javadoc. */
    public void testMethod5() {
        int arg = 7;
        boolean b = true;
        boolean bb = false;
        if (b) {
            if (!bb) {
                b = false;
            }
        }
        testMethod4(arg); // DECLARATION OF VARIABLE 'arg' SHOULD BE HERE (distance = 2)
    }
}
