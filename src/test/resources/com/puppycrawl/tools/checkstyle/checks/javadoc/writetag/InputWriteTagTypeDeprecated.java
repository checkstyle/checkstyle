/*
WriteTag
tag = @deprecated
tagFormat = \\S
tagSeverity = (default)info
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

// violation 2 lines below 'Javadoc tag @deprecated={@systemProperty x}'
/**
 * @deprecated {@systemProperty x}
 */
@Deprecated
class InputWriteTagTypeDeprecated {
}
