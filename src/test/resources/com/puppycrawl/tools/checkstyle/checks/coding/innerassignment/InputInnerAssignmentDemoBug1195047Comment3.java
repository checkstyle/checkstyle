/*
InnerAssignment


*/

package com.puppycrawl.tools.checkstyle.checks.coding.innerassignment;

public class InputInnerAssignmentDemoBug1195047Comment3 {
    public void demoBug1195047Comment3()
    {
        // inner assignment should flag all assignments to b or bb but none of those to i or j
        int y = 1;
        int b = 0;
        boolean bb;
        int i;

        if (bb = false) {} // violation, 'Inner assignments should be avoided'
        for (i = 0; bb = false; i = i + 1) {} // violation, 'Inner assignments should be avoided'
        while (bb = false) {} // violation, 'Inner assignments should be avoided'
        if ((bb = false)) {} // violation, 'Inner assignments should be avoided'
        for (int j = 0; (bb = false); j += 1) {} // violation, 'Inner assignments should be avoided'
        while ((bb = false)) {} // violation, 'Inner assignments should be avoided'
        i = (bb = false) ? (b = 2) : (b += 1);
        // 3 violations above:
        //    'Inner assignments should be avoided'
        //    'Inner assignments should be avoided'
        //    'Inner assignments should be avoided'
        i = (b += 1) + (b -= 1);
        // 2 violations above:
        //    'Inner assignments should be avoided'
        //    'Inner assignments should be avoided'
        do {i += 1;} while (bb = false); // violation, 'Inner assignments should be avoided'
    }
}
