//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.recordcomponentnumber;

/* Config:
 *
 * maxPublic = 8
 * maxProtected = 8
 * maxPackage = 8
 * maxPrivate = 8
 *
 */
public record InputRecordComponentNumberTopLevel2<T>() { // ok

}
