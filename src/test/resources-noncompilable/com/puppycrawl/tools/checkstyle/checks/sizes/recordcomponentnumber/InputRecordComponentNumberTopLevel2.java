//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.recordcomponentnumber;

/* Config:
 *
 * maxInPublicRecord = 8
 * maxInProtectedRecord = 8
 * maxInPackageRecord = 8
 * maxInPrivateRecord = 8
 *
 */
public record InputRecordComponentNumberTopLevel2<T>() { // ok

}
