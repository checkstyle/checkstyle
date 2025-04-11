/*
NoWhitespaceBefore

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

class InputNoWhitespaceBefore {
    public void testSpaceViolation() {
        "s".equals("s");
         "s".equals("s"); // violation
        "s" .equals("s"); // violation
        "s".equals ("s"); // violation
    }
}
