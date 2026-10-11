/*
IllegalBlockTag
tag = @todo
tagTextPattern = (default)^$
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, METHOD_DEF, \
CTOR_DEF, ENUM_CONSTANT_DEF, ANNOTATION_FIELD_DEF, RECORD_DEF, COMPACT_CTOR_DEF
violateExecutionOnNonTightHtml = true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.illegalblocktag;

// violation 2 lines below 'Unclosed HTML tag found: p'
/**
 * <p>
 */
class InputIllegalBlockTagNonTightHtml {

    public static final int CONST = 12;

}
