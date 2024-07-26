// non-compiled with javac: Compilable with Java17

package com.puppycrawl.tools.checkstyle.checks.naming.recordcomponentname;

/**
 * some javadoc.
 * Config:
 * format = "^[a-z]([a-z0-9][a-zA-Z0-9]*)?$"
 *
 */
public record InputRecordComponentName<E>(int _componentName, String componentName2) {}
// violation above 'Record component name '_componentName' must match pattern'

record InputRecordComponentName<E>(int Capital) {}
// 2 violations above:
//  'Top-level class InputRecordComponentName has to reside in its own source file.'
//  'Record component name 'Capital' must match pattern'
