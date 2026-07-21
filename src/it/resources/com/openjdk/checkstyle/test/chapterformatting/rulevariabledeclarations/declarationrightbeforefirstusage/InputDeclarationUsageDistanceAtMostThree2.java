package com.openjdk.checkstyle.test.chapterformatting.rulevariabledeclarations.declarationrightbeforefirstusage;

// violation first line 'Header mismatch'

/** Some javadoc. */
public class InputDeclarationUsageDistanceAtMostThree2 {

    /** Some javadoc. */
    public void testMethod6() {
        int blockNumWithSimilarVar = 3;
        int dist = 0;
        int index = 0;
        int block = 0;

        if (blockNumWithSimilarVar <= 1) {
            do {
                dist++;
                if (block > 4) {
                    break;
                }
                index++;
                block++;
            } while (index < 7);
        } else {
            while (index < 8) {
                dist += block;
                index++;
                block++;
            }
        }
    }

    /** Some javadoc. */
    public boolean testMethod7(int a) {
        boolean res;
        switch (a) {
            case 1: // violation ''case' construct must use '{}'s.'
                res = true;
                break;
            default: // violation ''default' construct must use '{}'s.'
                res = false;
        }
        return res;
    }

    /** Some javadoc. */
    public void testMethod8() {
        int b = 0;
        int c = 0;
        int m = 0;
        int n = 0;
        {
            c++;
            b++;
        }
        {
            n++; // DECLARATION OF VARIABLE 'n' SHOULD BE HERE (distance = 2)
            m++; // DECLARATION OF VARIABLE 'm' SHOULD BE HERE (distance = 3)
            b++;
        }
    }

    /** Some javadoc. */
    public void testMethod9() {
        boolean result = false;
        boolean b1 = true;
        boolean b2 = false;
        if (b1) {
            if (!b2) {
                result = true;
            }
            result = true;
        }
    }

    /** Some javadoc. */
    public boolean testMethod10() {
        boolean result;
        try {
            result = true;
        } catch (Exception e) {
            result = false;
        } finally {
            result = false;
        }
        return result;
    }

    /** Some javadoc. */
    public void testMethod11() {
        int a = 0;
        int b = 10;
        boolean result;
        try {
            b--;
        } catch (Exception e) {
            b++;
            result = false; // DECLARATION OF VARIABLE 'result' SHOULD BE HERE (distance = 2)
        } finally {
            a++;
        }
    }
}
