/*
OneTopLevelClass


*/


package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

public record InputOneTopLevelClassRecords() {
}

record TestRecord1() { // violation

}

@Deprecated
record TestRecord2() { // violation

}
