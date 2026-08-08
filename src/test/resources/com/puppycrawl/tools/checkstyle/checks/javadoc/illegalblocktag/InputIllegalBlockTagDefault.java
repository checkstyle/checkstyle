/*
IllegalBlockTag
tag = (default)null
tagTextPattern = (default)^$
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, METHOD_DEF, \
CTOR_DEF, ENUM_CONSTANT_DEF, ANNOTATION_FIELD_DEF, RECORD_DEF, COMPACT_CTOR_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.illegalblocktag;

/**
 * some doc
 * @todo something
 */
public class InputIllegalBlockTagDefault {
    /**
     * some doc
     * @since 1.1-beta
     */
    void method() {
    }
}
