/*
OneTopLevelClass


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

public record InputOneTopLevelClassRecords() {
}

record TestRecord1() { // violation

}

record TestRecord2() { // violation

}
