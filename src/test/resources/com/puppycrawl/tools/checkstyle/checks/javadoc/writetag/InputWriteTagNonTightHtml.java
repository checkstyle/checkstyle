/*
WriteTag
tag = (default)null
tagFormat = (default)null
tagSeverity = (default)info
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF
violateExecutionOnNonTightHtml = true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;
// violation 2 lines below 'Unclosed HTML tag found: p'
/**
 * <p>
 */
class InputWriteTagNonTightHtml {

    public static final int CONST = 12;

}
