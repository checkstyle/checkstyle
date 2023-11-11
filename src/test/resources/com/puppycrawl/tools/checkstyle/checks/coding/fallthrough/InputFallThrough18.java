/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough18 {

     void method1(int a) {
        switch (a) {}
        switch (a) {default: ; }
        switch (a) {default: {}}
        switch (a) {
            default:
        }
        switch (a) {
            default:
            {}
        }
        switch (a) {
            default:
            {
            }
        }
    }

}
