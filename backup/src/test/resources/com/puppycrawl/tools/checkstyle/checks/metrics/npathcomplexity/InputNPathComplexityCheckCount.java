/*
NPathComplexity
max = 20


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

public class InputNPathComplexityCheckCount{
    public void method() { // violation 'NPath Complexity is 30 (max allowed is 20)'
        try {}
        catch (IllegalArgumentException ex) {}
        try {}
        catch (IllegalArgumentException ex) {}
        try {}
        catch (IllegalArgumentException ex) {}
        try {}
        catch (IllegalArgumentException ex) {}
    }

    int method2() throws InterruptedException {
        // violation above 'NPath Complexity is 72 (max allowed is 20)'
        int x = 1;
        int a = 2;
        while (true) {
            try {
                if (x > 0) {
                    break;
                } else if (x < 0) {
                    ;
                } else {
                    break;
                }
                switch (a)
                {
                case 0:
                    break;
                default:
                    break;
                }
            }
            catch (Exception e)
            {
                break;
            }
        }

        synchronized (this) {
            do {
                x = 2;
            } while (x == 2);
        }

        this.wait(666);

        for (int k = 0; k < 1; k++) {
            String innerBlockVariable = "";
        }

        if (System.currentTimeMillis() > 1000)
            return 1;
        else
            return 2;
    }

    void method3(char c, int i) { // violation 'NPath Complexity is 23 (max allowed is 20)'
        while (true) {
            switch (c) {
            case 'a':
            case 'b':
                i++;
            case 'c':
                break;
            case 'd':
                return;
            case 'e':
                continue;
            case 'f':
                if (true) return;
            case 'g':
                try {
                    i++;
                    break;
                } catch (RuntimeException e) {
                } catch (Error e) {
                    return;
                }
            case 'h':
                switch (i) {
                case 1:
                    continue;
                case 2:
                    i++;
                case 3:
                    return;
                }
            case 'i':
                switch (i) {
                case 1:
                    continue;
                case 2:
                    i++;
                    break;
                case 3:
                    return;
                }
                break;
            case 'A':
                i++;
            case 'B':
                i++;
            default:
                i++;
                break;
            }
        }
    }
}
