/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

//non-compiled with javac: Compilable with Java19
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough5 {
    void exhaustiveStatementSane(Object o) {
        switch (o) {
            case Object obj: ;
            // fall thru
        }
        switch (o) { // violation below 'Fall .* from the last branch of the switch statement'
            case null, Object obj: ;
        }
        switch (o) { // violation below 'Fall .* from the last branch of the switch statement'
            case Object obj, null: ;
        }
        switch (o) {
            case Object obj, null: ; // fall thru
        }
    }
}
