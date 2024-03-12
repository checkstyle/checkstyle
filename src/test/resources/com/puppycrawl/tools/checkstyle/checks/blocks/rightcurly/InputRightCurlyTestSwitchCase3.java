/*
RightCurly
option = ALONE
tokens = LITERAL_SWITCH, LITERAL_IF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestSwitchCase3 {

    public void method() {
        int x = 0;
        switch (x) { } // violation ''}' at column 22 should be alone on a line'
        switch (x) {
        } int a; // violation ''}' at column 9 should be alone on a line'
        switch (x) {
            case(1): x = 0; break; } // violation ''}' at column 36 should be alone on a line'
        switch (x)
        {
        }
        switch (x)
        {case(1): x=1;break;
        } int b; // violation ''}' at column 9 should be alone on a line'
        switch (x) {case(2): break; } int c;
        // violation above ''}' at column 37 should be alone on a line'
        switch (x) { } int d; // violation ''}' at column 22 should be alone on a line'
        switch (x) { } // violation ''}' at column 22 should be alone on a line'
    }

    public void someMethod() {
        int x = 90;
        if (7>x) {
           switch (x) {
               case(1):
                   break;}} // 2 violations
    }

    public void someMethod2() {
        int x = 90;
        if (7>x) {
           switch (x) { case(1): break;}} // 2 violations
    }

    public void someMethod3() {
        int x = 90;
        if (7>x) {switch (x) {case(1): break;}
        } // violation above ''}' at column 46 should be alone on a line'
        if (7>x) {switch (x) { }} // 2 violations
        if (7>x) {switch (x) {
            }} // 2 violations
        if (7>x) {switch (x) {
            }
       }
       if (7>x) {switch (x) {
            }
       }
    }
}
