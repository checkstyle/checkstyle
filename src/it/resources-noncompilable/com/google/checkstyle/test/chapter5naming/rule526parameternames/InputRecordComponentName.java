//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.recordcomponentname;

/* Config:
 * format = "^[a-z]([a-z0-9][a-zA-Z0-9]*)?$"
 *
 */
public record InputRecordComponentName<t>(int _componentName, // warn
                                          String componentName2) {
}

record InputRecordComponentName<t>(int Capital) { // warn
}
