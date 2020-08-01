//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.outertypenumber;

/* Config:
 * max = 1
 *
 */
class InputOuterTypeNumberRecords { }

record MyTestRecord() {} // violation
