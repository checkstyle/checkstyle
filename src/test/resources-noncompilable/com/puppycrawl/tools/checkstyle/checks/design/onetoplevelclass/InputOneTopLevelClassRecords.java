//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

/* Config:
 *
 * default
 */
public record InputOneTopLevelClassRecords() {
}

record TestRecord1() { // violation

}

record TestRecord2() { // violation #2

}
