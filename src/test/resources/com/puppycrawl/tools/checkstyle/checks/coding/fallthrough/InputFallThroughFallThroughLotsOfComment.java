/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughFallThroughLotsOfComment {

    void method(int lineNumber) {
        switch (lineNumber) {
            case 3:
                Object pi = null;
                int line = 2;
                // fall-through intended - the line contains already the first entries
                // the following 4 lines contain additional satellite ids
            case 4:
            case 5:
            case 6:
            case 7: {
                int lineLength =  2;
                int startIdx = 9;
                while (lineLength++ < 2&& (startIdx + 3) <= lineLength) {
                    String satId;
                    startIdx += 3;
                }
                break;
            }

            case 14:
                // ignore additional custom fields
                break;

            // load base numbers for the standard deviations of
            // position/velocity/clock components
            case 15: {
                // String base = line.substring(3, 13).trim();
                // if (!base.equals("0.0000000")) {
                //    // (mm or 10**-4 mm/sec)
                //    pi.posVelBase = Double.valueOf(base);
                // }

                // base = line.substring(14, 26).trim();
                // if (!base.equals("0.000000000")) {
                //    // (psec or 10**-4 psec/sec)
                //    pi.clockBase = Double.valueOf(base);
                // }
            }
            case 16: // violation 'Fall through from previous branch of the switch statement'
            case 17:
            case 18:
                // ignore additional custom parameters
                break;

            case 19:
            case 20:
            case 21:
            case 22:
                // ignore comment lines
                break;
            default:
                // ignore -> method should only be called up to line 22
                break;
        }

        // CHECKSTYLE: resume FallThrough check

    }
}
