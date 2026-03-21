/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

// Java19
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough11 {
    void exhaustiveStatementSane(Object o) {
        switch (o) {
            case Object obj:
                ;
                break;
            // fall thru
        }
        switch (o) {
            case null:
                ;
                break; // violation below 'Fall .* from the last branch of the switch statement'
            case Object obj:
                ;
        }
        switch (o) {
            case Object obj:
                ;
                break; // violation below 'Fall .* from the last branch of the switch statement'
            case null:
                ;
        }
        switch (o) {
            case Object obj:
                ;
                break; // violation below 'Fall .* from the last branch of the switch statement'
            case null:
                ;
        }
    }
}
