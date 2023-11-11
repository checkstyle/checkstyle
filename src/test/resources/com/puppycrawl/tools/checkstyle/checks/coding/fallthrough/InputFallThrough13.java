/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough13 {

    void Test(){
        int j=0;
        switch (j) {default: j++;}
    }

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
        switch(c) { case 0: {}
            case 1: { // violation 'Fall\ through from previous branch of the switch statement.'
        }
        }
    }

    public static void method0() {
           int mode = 0;
            switch (mode) {
              case 1:
                   int x = 1;
                   break;
               default :
                   x = 0; }
       }

}
