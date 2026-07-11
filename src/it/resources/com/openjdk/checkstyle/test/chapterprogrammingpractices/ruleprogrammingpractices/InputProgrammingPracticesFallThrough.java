package com.openjdk.checkstyle.test.chapterprogrammingpractices.ruleprogrammingpractices;

// violation first line 'Header mismatch'

public class InputProgrammingPracticesFallThrough {
    public void foo() throws Exception {
        int i = 0;
        while (i >= 0) {
            switch (i) {
                case 1: {
                    i++;
                }
                // fall through
                case 2: {
                    i++;
                    break;
                }
                case 3: {
                    i++;
                    return;
                }
                case 4: {
                    i++;
                    throw new Exception();
                }
                case 5: {
                    i++;
                }
                case 6: { // violation 'Fall\ through from previous branch of the switch'
                    break;
                }
                case 7: {
                    i++;
                    continue;
                }
                case 11: {
                    i++;
                }
            }
        }
    }
}
