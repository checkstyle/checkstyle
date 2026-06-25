/*
WriteTag
tag = @author
tagFormat = \\S
tagSeverity = (default)info
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

// violation 2 lines below 'Javadoc tag @author=<p>Jane Doe</p>     {@summary a concise summary}'
/**
 * @author <p>Jane Doe</p>     {@summary a concise summary}
 */
class InputWriteTagTypeAuthor {
}
