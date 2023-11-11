/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough20 {
   void method1(int a) {
        switch (a) {}
        switch (a) {case 1: ; }
        switch (a) {case 1:{}}
        switch (a) {
            case 1:
        }
        switch (a) {
            case 1:
            {}
        }
        switch (a) {
            case 1:
            {
            }
        }
    }

    public void method2(char c) {
        switch(c) { case 0: }
        switch(c) { case 0: {} method1(1); }
        switch(c) { case 0: method1(0); {} }
        switch(c) { case 0: case 1: {} }
        switch(c) { case 0: {} case 1: { // violation 'Fall\ through from previous branch of the switch statement.'
        }
        }
    }
}
