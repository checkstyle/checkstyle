package com.openjdk.checkstyle.test.chapterformatting.rulebraces;

// violation first line 'Header mismatch*'

public class InputBracesDosAndDonts {
    int[][] matrix;
    void method() {

    }

    void method1()
    {

    }

    public void styleGuideDo() {
        try {
            method();
        } catch (Exception e) {

        }

        int sum = 0;

        for (int[] row : matrix) {
            for (int val : row) {
                sum += val;
            }
        }
    }

    public void styleGuideDonts() {
        int x = 0;
        int sum = 0;

        try {
            method1();
        } // violation, 'should be on the same line'
        catch (Exception e) {

        }

        boolean flag = true;

        if (flag) // violation ''if' construct must use '{}'s.'
            x = 1;

        for (int[] row : matrix) {
            for (int val : row) // violation ''for' construct must use '{}'s.'
                sum += val;
        }
    }
}
