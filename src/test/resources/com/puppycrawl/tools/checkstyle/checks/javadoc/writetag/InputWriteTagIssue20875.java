/*
WriteTag
tag = @since
tagFormat = \d+
tagSeverity = ignore
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

// violation 4 lines below 'missing @since tag.*'
/**
 * @version 1
 */
@Deprecated
public class InputWriteTagIssue20875 {
}
