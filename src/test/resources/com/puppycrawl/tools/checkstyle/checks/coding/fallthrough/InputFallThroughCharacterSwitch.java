/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughCharacterSwitch
{
    void foo(char c, int i) {
        while (true) {
            switch (c) {
            case 'a':
            case 'b':
                i++;
            case 'c': // violation 'Fall\ through from previous branch of the switch statement.'
                String.valueOf(i);
                break;
            case 'd':
                return;
            case 'e':
                continue;
            case 'f':
                if (true) {
                    return;
                }
            case 'g': // violation 'Fall\ through from previous branch of the switch statement.'
                try {
                    i++;
                    break;
                } catch (RuntimeException e) {
                } catch (Error e) {
                    return;
                }
            case 'h': // violation 'Fall\ through from previous branch of the switch statement.'
                switch (i) {
                case 1:
                    continue;
                case 2:
                    i++;
                case 3: // violation 'Fall\ through from previous branch of the switch statement.'
                    String.valueOf(i);
                    return;
                }
            case 'i': // violation 'Fall\ through from previous branch of the switch statement.'
                switch (i) {
                case 1:
                    continue;
                case 2:
                    i++;
                    break;
                case 3:
                    String.valueOf(i);
                    return;
                }
                break;
            case 'A':
                i++;
                // FALL-THRU (case-sensitive)
            case 'B': // violation 'Fall\ through from previous branch of the switch statement.'
                i++;
                // fall-through
            default:
                i++;
                break;
            }
        }
    }
}
