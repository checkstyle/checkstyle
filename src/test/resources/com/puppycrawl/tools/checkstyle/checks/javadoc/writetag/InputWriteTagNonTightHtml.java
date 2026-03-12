/*
WriteTag
tag = (default)null
tagFormat = (default)null
tagSeverity = (default)info
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, \
BLOCK_COMMENT_BEGIN
violateExecutionOnNonTightHtml = true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

class InputWriteTagNonTightHtml {

    /**
     * <p> // violation, 'Unclosed HTML tag found: p'
     */
    public static final int CONST = 12;

}
