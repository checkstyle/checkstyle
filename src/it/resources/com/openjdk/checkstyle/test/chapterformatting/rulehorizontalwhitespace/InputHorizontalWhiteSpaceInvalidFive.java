package com.openjdk.checkstyle.test.chapterformatting.rulehorizontalwhitespace;

// violation first line 'Header mismatch'

public class InputHorizontalWhiteSpaceInvalidFive {
    int foo()   { // violation 'Use a single space'
        return  1; // violation 'Use a single space'
    }

    int fun1() {
        return 3;
    }

    void  fun2() {} // violation 'Use a single space'

    public void temp() {
        if  (1 > 2)  {
            // 2 violations above:
            // 'Use a single space'
            // 'Use a single space'
        }

        for  (int i = 0; i < 10;  i++)  {
            // 3 violations above:
            // 'Use a single space'
            // 'Use a single space'
            // 'Use a single space'
        }

        int a = 0;

        switch  (a)  {
            // 2 violations above:
            // 'Use a single space'
            // 'Use a single space'
            case 1:  { // violation 'Use a single space'
                break;
            }
            default:   { // violation 'Use a single space'
                break;
            }
        }
    }
}
