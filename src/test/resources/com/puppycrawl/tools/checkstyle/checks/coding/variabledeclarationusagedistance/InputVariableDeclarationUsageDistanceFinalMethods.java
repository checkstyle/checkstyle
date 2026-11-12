/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceFinalMethods {

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
        }
        else {
            while (index < 8) {
                dist += block;
                index++;
                block++;
            }
        }
    }

    public boolean testMethod7(int a) {
        boolean res;
        switch (a) {
            case 1:
                res = true;
                break;
            default:
                res = false;
        }
        return res;
    }

    public void testMethod8() {
        int b = 0;
        int c = 0;
        int m = 0; // violation 'Distance .* is 3.'
        int n = 0; // violation 'Distance .* is 2.'
        {
            c++;
            b++;
        }
        {
            n++;
            m++;
            b++;
        }
    }

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

    public boolean testMethod10() {
        boolean result;
        try {
            result = true;
        }
        catch (Exception e) {
            result = false;
        }
        finally {
            result = false;
        }
        return result;
    }

    public void testMethod11() {
        int a = 0;
        int b = 10;
        boolean result; // violation 'Distance .* is 2.'
        try {
            b--;
        }
        catch (Exception e) {
            b++;
            result = false;
        }
        finally {
            a++;
        }
    }
}
