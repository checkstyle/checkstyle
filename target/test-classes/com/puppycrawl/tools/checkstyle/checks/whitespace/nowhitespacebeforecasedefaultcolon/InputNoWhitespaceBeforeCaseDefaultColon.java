/*
NoWhitespaceBeforeCaseDefaultColon



*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebeforecasedefaultcolon;

import java.util.Calendar;

public class InputNoWhitespaceBeforeCaseDefaultColon {
    {
        switch(1) {
            case 1 : // violation
                break;
            case 2:
                break;
            default : // violation
                break;
        }

        switch(2) {
            case 2:
                break;
            default:
                break;

        }

        switch(3) {
            case 3/* space after */ : // violation
                break;
            default/* no space after */:
                break;
        }

        switch(4) {
            case 4
                    : // violation
                break;
            default:
                switch(5) {
                    case 5 : // violation
                        break;
                    case 6
: // violation
                        break;
                    case 7
                          : // violation
                        break;
                    default:
                        break;
                }
                break;
        }

        switch(8) {
            case 8/* no space after */:
                break;
            default/* space after */ : // violation
                break;
        }

        switch (Calendar.MONDAY) {
            case Calendar.TUESDAY    : // violation
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
                    10 : // violation
                break;
            default
                    : // violation
                break;

        }

        switch(11) {
            case
                    /* comment */ 11 : // violation
                break;
            default
                    /* comment */ : // violation
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
