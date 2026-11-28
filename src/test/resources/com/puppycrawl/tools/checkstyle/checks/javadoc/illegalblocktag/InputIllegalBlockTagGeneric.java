/*
IllegalBlockTag
tag = @since
tagTextPattern = ^[0-9.]+$
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, METHOD_DEF, \
CTOR_DEF, ENUM_CONSTANT_DEF, ANNOTATION_FIELD_DEF, RECORD_DEF, COMPACT_CTOR_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.illegalblocktag;

/**
 * Some javadoc.
 *
 * @since 1.0
 */
public class InputIllegalBlockTagGeneric {
    // violation 4 lines below 'Block tag 'since' is matched illegal pattern'
    /**
     * Some javadoc.
     *
     * @since next
     */
    <T> void generic(T value) {
    }

    // violation 4 lines below 'Block tag 'since' is matched illegal pattern'
    /**
     * Some javadoc.
     *
     * @since next
     */
    public static <T> T genericWithModifiers(T value) {
        return value;
    }

    // violation 4 lines below 'Block tag 'since' is matched illegal pattern'
    /**
     * Some javadoc.
     *
     * @since next
     */
    <T> InputIllegalBlockTagGeneric(T value) {
    }

    /**
     * Some javadoc.
     *
     * @since 2.0
     */
    <T> void genericValid(T value) {
    }
}
