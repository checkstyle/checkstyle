/*
NoWhitespaceBefore


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

class InputNoWhitespaceBefore22 {

    Boolean equals;

    public void testSpaceViolationMethodCall() {
        // Valid cases
        " ".equals("");
        "".equals(" ");
        "".equals("");

        // Violations
        "".equals(""); // violation is preceded with whitespace.
        "" .equals(""); // violation is preceded with whitespace.
        "". equals(""); // violation is preceded with whitespace.
        "".equals (""); // violation is preceded with whitespace.
        "".equals( ""); // violation is preceded with whitespace.
        "".equals("" ); // violation is preceded with whitespace.
        "".equals("") ; // violation is preceded with whitespace.
    }

    public void testSpaceViolationVarAssignment() {
        // Valid
        equals = " ".equals("");
        equals = "".equals("");

        // Violations
        equals = "".equals(""); // violation is preceded with whitespace.
        equals  = "".equals(""); // violation is preceded with whitespace.
        equals =  "".equals(""); // violation is preceded with whitespace.
    }

    public void testSpaceViolationVarDeclaration() {
        // Valid
        boolean e = "".equals("");
        boolean e3 = "".equals("");
        e3 = "".equals("");

        // Violations
        boolean e4 = "".equals(""); // violation is preceded with whitespace.
        boolean e1  = "".equals(""); // violation is preceded with whitespace.
        boolean  e2 = "".equals(""); // violation is preceded with whitespace.
        e3 = "".equals(""); // violation is preceded with whitespace.
        e3  = "".equals(""); // violation is preceded with whitespace.
        e3 =  "".equals(""); // violation is preceded with whitespace.
    }

    public void testSpaceViolationTab() {
        // Valid
        "".equals("");

        // Violations (with tabs)
        "".equals(""); // violation is preceded with whitespace.
        "".equals(""); // violation is preceded with whitespace.
    }


    // Additional test cases with valid/invalid pattern
    public void testArrayAccess() {
        int[] arr = new int[10];

        // Valid
        int a = arr[0];

        // Violations
        int x = arr [0]; // violation is preceded with whitespace.
        int y = arr[ 0]; // violation is preceded with whitespace.
        int z = arr [ 0]; // violation is preceded with whitespace.
    }

    public void testGenerics() {
        java.util.List<String> list = new java.util.ArrayList<>();

        // Valid
        list.add("test");

        // Violations
        list .add("test"); // violation is preceded with whitespace.
        list. add("test"); // violation is preceded with whitespace.
    }

    public void testLambda() {
        // Valid
        Runnable r = () -> System.out.println();

        // Violation
        Runnable r2 = () -> System.out .println(); // violation is preceded with whitespace.
    }

    public void testMethodReference() {
        // Valid
        java.util.function.Function<String, String> f1 = String::valueOf;

        // Violation
        java.util.function.Function<String, String> f2 = String ::valueOf; // violation is preceded with whitespace.
    }

    public void testNestedCalls() {
        String s = "hello";

        // Valid
        s.substring(1).trim();
        s.substring(1).trim();

        // Violations
        s.substring(1). trim(); // violation is preceded with whitespace.
        s.substring(1 ).trim(); // violation is preceded with whitespace.
    }

    public void testMultipleDots() {
        String s = "hello";

        // Valid
        s.substring(1).substring(1).substring(1);

        // Violations
        s.substring(1).substring(1). substring(1); // violation is preceded with whitespace.
        s.substring(1) .substring(1).substring(1); // violation is preceded with whitespace.
    }

    public void testWithOtherOperators() {
        // Valid
        String s1 = "a" + "b".toString();
        int x = 1 + 2 * 3;

        // Violation
        String s2 = "a" + "b". toString(); // violation is preceded with whitespace.
    }

    public void testInControlStructures() {
        // Valid
        if ("test".equals("test")) {
            System.out.println();
        }

        // Violations
        if ("test". equals("test")) { // violation is preceded with whitespace.
            System.out.println();
        }

        while (true) {
            break ;
        }
    }

    public void testInTryCatch() {
        // Valid
        try {
            // do something
        } catch (Exception e) {
            // handle
        }

        // Violation
        try {
            // do something
        } catch (Exception e ) { // violation is preceded with whitespace.
            // handle
        }
    }

    public void testInAnnotations() {
        // Valid
        @SuppressWarnings("unchecked")
        Object o1 = new Object();

        // Violation
        @SuppressWarnings ("unchecked") // violation is preceded with whitespace.
        Object o2 = new Object();
    }

    public void testInTypeCast() {
        Object o = "test";

        // Valid
        String s1 = (String) o;

        // Violation
        String s2 = (String ) o; // violation is preceded with whitespace.
    }

    public void testInSwitch() {
        // Valid
        switch (1) {
            case 1:
                break;
        }

        // Violation
        switch (1 ) { // violation is preceded with whitespace.
            case 1:
                break;
        }
    }

    public void testInSynchronized() {
        // Valid
        synchronized (this) {
            // do something
        }

        // Violation
        synchronized (this ) { // violation is preceded with whitespace.
            // do something
        }
    }

    public void testInAssert() {
        // Valid
        assert true : "message";

        // Violation
        assert true : "message" ; // violation is preceded with whitespace.
    }

    public void testInReturn() {
        // Valid (no return value)
        return;
    }
    public void testInReturn2() {
        // Violation
        return ; // violation is preceded with whitespace.
    }

    public void testInThrow() throws Exception {
        // Valid
        throw new Exception();
    }

    public void testInThrow2() throws Exception {
        // Violation
        throw new Exception() ; // violation is preceded with whitespace.
    }

    public void testInArrayInitializer() {
        // Valid
        int[] arr1 = new int[] {1, 2, 3};

        // Violation
        int[] arr2 = new int[] {1, 2, 3 } ; // violation is preceded with whitespace.
    }
}
