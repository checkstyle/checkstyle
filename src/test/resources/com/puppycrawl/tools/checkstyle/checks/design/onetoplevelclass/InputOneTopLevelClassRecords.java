/*
OneTopLevelClass


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

public record InputOneTopLevelClassRecords() {
}

record TestRecord1() { // violation 'Top-level class TestRecord1 has to reside in its own source file.'

}

record TestRecord2() { // violation 'Top-level class TestRecord2 has to reside in its own source file.'

}
