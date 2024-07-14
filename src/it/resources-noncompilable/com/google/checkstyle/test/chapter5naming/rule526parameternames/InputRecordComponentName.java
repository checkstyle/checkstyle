// non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.recordcomponentname;

/* Config:
 * format = "^[a-z]([a-z0-9][a-zA-Z0-9]*)?$"
 *
 */
// violation below 'Record component name '_componentName' must match pattern'
public record InputRecordComponentName<t>(int _componentName, String componentName2) {}

// violation below 'Record component name 'Capital' must match pattern'
record InputRecordComponentName<t>(int Capital) {}
