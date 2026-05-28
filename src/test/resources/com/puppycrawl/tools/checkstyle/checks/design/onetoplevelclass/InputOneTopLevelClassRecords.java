/*
OneTopLevelClass


*/


package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

public record InputOneTopLevelClassRecords() {
}

// violation below 'Top-level class TestRecord1 has to reside in its own source file.'
record TestRecord1() {

}

// violation 2 lines below 'Top-level class TestRecord2 has to reside in its own source file.'
@Deprecated
record TestRecord2() {

}
