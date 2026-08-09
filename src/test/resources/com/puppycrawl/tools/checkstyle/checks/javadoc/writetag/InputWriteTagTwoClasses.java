/*
WriteTag
tag = @incomplete
tagFormat = \\S
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * @incomplete test
 */
class InputWriteTagTwoClasses {
}

// violation 4 lines below 'Javadoc comment is missing @incomplete tag.'
/**
 * No incomplete tag here.
 */
class InputWriteTagTwoClassesExtra {
}
