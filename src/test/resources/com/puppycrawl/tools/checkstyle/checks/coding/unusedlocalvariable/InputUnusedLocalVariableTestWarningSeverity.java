/*
UnusedLocalVariable
severity = warning


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableTestWarningSeverity {

    void m() {
     @Test.A Outer p1 = new @Test.A Outer();
     @Test.A Outer.@Test.B Inner p2 = p1.new @Test.B Inner();
     // ok above until https://github.com/checkstyle/checkstyle/issues/12980
    }

}
