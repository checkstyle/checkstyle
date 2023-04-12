/*
UnusedLocalVariable
severity = warning


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableTestWarningSeverity {

    void m() {
     @Test.A Outer p1 = new @Test.A Outer(); // ok
     @Test.A Outer.@Test.B Inner p2 = p1.new @Test.B Inner();
     // violation above but severity is set to warning
    }

}



