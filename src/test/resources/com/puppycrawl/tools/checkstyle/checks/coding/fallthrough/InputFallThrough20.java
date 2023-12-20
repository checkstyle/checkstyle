/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough20 {
    void method1(int a) {
        switch (a) {
        }
        switch (a) {
            case 1:
                ;   // violation above 'Fall\ through from previous branch of the switch statement.'
        }


    }
}
