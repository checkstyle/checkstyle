/*
InnerAssignment


*/

package com.puppycrawl.tools.checkstyle.checks.coding.innerassignment;


public class InputInnerAssignmentNotInLoopContext {
    int value = 3;
    boolean b = 1 > (value = 2); // violation
}
