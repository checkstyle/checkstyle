/*
NoWhitespaceBeforeCaseDefaultColon



*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebeforecasedefaultcolon;

import java.util.Calendar;

public class InputNoWhitespaceBeforeCaseDefaultColon {
    {
        switch(1) {
            case 1 : // violation '':' is preceded with whitespace.'
                break;
            case 2:
                break;
            default : // violation '':' is preceded with whitespace.'
                break;
        }

        switch(2) {
            case 2:
                break;
            default:
                break;

        }

        switch(3) {
            case 3/* space after */ : // violation '':' is preceded with whitespace.'
                break;
            default/* no space after */:
                break;
        }

        switch(4) {
            case 4
                    : // violation '':' is preceded with whitespace.'
                break;
            default:
                switch(5) {
                    case 5 : // violation '':' is preceded with whitespace.'
                        break;
                    case 6
: // violation '':' is preceded with whitespace.'
                        break;
                    case 7
                          : // violation '':' is preceded with whitespace.'
                        break;
                    default:
                        break;
                }
                break;
        }

        switch(8) {
            case 8/* no space after */:
                break;
            default/* space after */ : // violation '':' is preceded with whitespace.'
                break;
        }

        switch (Calendar.MONDAY) {
            case Calendar.TUESDAY    : // violation '':' is preceded with whitespace.'
                break;
            case Calendar.WEDNESDAY:
                break;
            default:
                break;
        }

        switch(9) {
            case
                    9:
                break;
            case
                    10 : // violation '':' is preceded with whitespace.'
                break;
            default
                    : // violation '':' is preceded with whitespace.'
                break;

        }

        switch(11) {
            case
                    /* comment */ 11 : // violation '':' is preceded with whitespace.'
                break;
            default
                    /* comment */ : // violation '':' is preceded with whitespace.'
                break;

        }

         for (int var1 : new int[]{}) {}

         for (int var2: new int[]{}) {}

         int i = true ? 0 : 1;

         int j = false ? 0
                 : 1;

        for (int t = 10; --t >= 0; ) {
        }

    }
}
