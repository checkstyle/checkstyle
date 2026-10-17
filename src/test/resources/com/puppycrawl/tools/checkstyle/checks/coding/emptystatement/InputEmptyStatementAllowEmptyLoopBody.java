/*
EmptyStatement
allowEmptyLoopBody = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.emptystatement;

public class InputEmptyStatementAllowEmptyLoopBody {

    public void emptyLoopBodies() {
        int i = 0;

        // ok, empty 'for' body is allowed
        for (i = 0; i < 5; i++);

        // ok, empty 'while' body is allowed
        while (i < 10);

        // ok, empty 'for' body is allowed, even on a separate line
        for (i = 0; i < 5; i++)
            ;

        // violation below 'Empty statement.'
        if (i > 3);

        // violation 2 lines below 'Empty statement.'
        do
            ;
        while (i < 10);

        // violation below 'Empty statement.'
        ;
    }

    // violation below 'Empty statement.'
    ;

}
