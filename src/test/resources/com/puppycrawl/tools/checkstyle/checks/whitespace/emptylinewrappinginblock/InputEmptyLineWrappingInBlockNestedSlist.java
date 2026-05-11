/*
EmptyLineWrappingInBlock
tokens = SLIST, CASE_GROUP
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlockNestedSlist {

    public void method(int i) {
        { // violation ''{' must have exactly one empty line after.'
            int x = 0;
        } // violation ''}' must have exactly one empty line before'

        { // violation ''{' must have exactly one empty line after.'
            int x = 0;
        } // violation ''}' must have exactly one empty line before'


        {

            switch (i) {

                case 0: {

                    int y = 0;

                }
                default:
                    break;
            }

        }
    }

}
