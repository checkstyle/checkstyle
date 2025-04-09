/*
NoWhitespaceBefore

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

class InputNoWhitespaceBefore {
    public void testSpaceViolation() {
        boolean e = "s".equals("s"); // violation
    }
}
