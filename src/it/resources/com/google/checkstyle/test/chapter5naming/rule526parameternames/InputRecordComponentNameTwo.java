// Java17

package com.google.checkstyle.test.chapter5naming.rule526parameternames;

/**
 * Some javadoc.
 * Config:
 * format = "^[a-z]([a-z0-9][a-zA-Z0-9]*)?$"
 *
 */
public record InputRecordComponentNameTwo<E>(int _componentName, String componentName2) {}
// violation above 'Record component name '_componentName' must match pattern'

record InputRecordComponentNameVariant<E>(int Capital) {}
// 2 violations above:
//  'Top-level class InputRecordComponentNameVariant has to reside in its own source file.'
//  'Record component name 'Capital' must match pattern'
