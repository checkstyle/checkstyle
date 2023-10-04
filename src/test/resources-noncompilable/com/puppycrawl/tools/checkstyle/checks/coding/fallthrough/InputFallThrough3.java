/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough3
{
    int hasYield() {
        int i = 0;
        return switch (hashCode()) {
        case 1:
            i++;
        case 2: // violation 'Fall\ through from previous branch of the switch statement.'
            yield 2;
        case 3:
            // fall through
        case 4:
            yield 5;
        default:
            yield -1;
        };
    }
}
