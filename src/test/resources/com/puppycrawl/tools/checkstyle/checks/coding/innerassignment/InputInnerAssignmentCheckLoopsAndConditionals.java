/*
InnerAssignment


*/

package com.puppycrawl.tools.checkstyle.checks.coding.innerassignment;

public class InputInnerAssignmentCheckLoopsAndConditionals {
    public void checkLoopsAndConditionals()
    {
        // inner assignment should flag all assignments to b or bb but none of those to i or j
        int y = 1;
        int b = 0;
        boolean bb;
        int i;

        if (bb = false) {} // violation
        for (i = 0; bb = false; i = i + 1) {} // violation
        while (bb = false) {} // violation
        if ((bb = false)) {} // violation
        for (int j = 0; (bb = false); j += 1) {} // violation
        while ((bb = false)) {} // violation
        i = (bb = false) ? (b = 2) : (b += 1); // 3 violations
        i = (b += 1) + (b -= 1); // 2 violations
        do {i += 1;} while (bb = false); // violation
    }
}
