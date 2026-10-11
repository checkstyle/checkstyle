/*
IllegalBlockTag
tag = @todo
tagTextPattern = before.*after
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, METHOD_DEF, \
CTOR_DEF, ENUM_CONSTANT_DEF, ANNOTATION_FIELD_DEF, RECORD_DEF, COMPACT_CTOR_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.illegalblocktag;

public class InputIllegalBlockTagFullContent {
    /**
     * some doc
     * @todo before {@code mid} after
     */
    void allowedFullContent() {
    }

    // violation 3 lines below 'Block tag 'todo' is matched illegal pattern'
    /**
     * some doc
     * @todo before only
     */
    void truncatedContent() {
    }
}
