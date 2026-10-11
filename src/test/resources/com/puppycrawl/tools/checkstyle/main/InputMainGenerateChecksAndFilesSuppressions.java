package com.puppycrawl.tools.checkstyle.main;

public class InputMainGenerateChecksAndFilesSuppressions {
    private int low = 0; // violation
    private int high = 0; // violation

    private int arr[] = null; // violation

    // violation below 'Throwing 'RuntimeException' is not allowed'
    public void test1() throws RuntimeException {
        // violation 3 lines below 'Nested for depth is 2 (max allowed 1).'
        for (int i = low; i < 100; i++) {
            for (int j = low; j < 100; j++) {
                for (int k = low; k < 100; k++) {

                }
            }
        }
    }

    public void test2(int val) throws Error { // violation 'Throwing 'Error' is not allowed'
        high = val;
    }

    public void test3() throws Throwable {} // violation 'Throwing 'Throwable' is not allowed'

    public void Test4() {} // violation 'Name 'Example4' must match pattern '^[a-z][a-zA-Z0-9]*$''
}
