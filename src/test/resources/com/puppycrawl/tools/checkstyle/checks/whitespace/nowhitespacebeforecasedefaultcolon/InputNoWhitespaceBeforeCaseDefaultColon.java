package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebeforecasedefaultcolon;

/*
* Config = default
*/
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
            case 3 /* space around*/ : // violation
                break;
            default/* no space around */:
                break;
        }

        switch(4) {
            case 4
                    :
                break;
            default:
                switch(5) {
                    case 5 : // violation
                        break;
                    default:
                        break;
                }
                break;
        }

         for (int var1 : new int[]{}) {}

         for (int var2: new int[]{}) {}
    }
}
