/*
IllegalBlockTag
tag = @todo
tagTextPattern = (default)^$
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, METHOD_DEF, \
CTOR_DEF, ENUM_CONSTANT_DEF, ANNOTATION_FIELD_DEF, RECORD_DEF, COMPACT_CTOR_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.illegalblocktag;

// violation 3 lines below 'Block tag 'todo' is matched illegal pattern'
/**
 * some doc
 * @todo remove this tag
 */
public class InputIllegalBlockTagPresence {
    /**
     * some doc
     * @since 1.0
     */
    void okMethod() {
    }

    // violation 3 lines below 'Block tag 'todo' is matched illegal pattern'
    /**
     * some doc
     * @todo fix later
     */
    void badMethod() {
    }
}
