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
        return switch (hashCode()) {
        case 1:
            yield 1;
        case 2: // OK
            yield 2;
        case 3: // OK
            // fall through
        case 4: // OK
        default: // OK, case does not contain code
            yield -1;
        };
    }
}
