/*
IllegalBlockTag
tag = @todo
tagTextPattern = (default)^$
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, METHOD_DEF, \
CTOR_DEF, ENUM_CONSTANT_DEF, ANNOTATION_FIELD_DEF, RECORD_DEF, COMPACT_CTOR_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.illegalblocktag;

public enum InputIllegalBlockTagEnumConstant {
    // violation 2 lines below 'Block tag 'todo' is matched illegal pattern'
    /**
     * @todo bare
     */
    A,
    B;
}
