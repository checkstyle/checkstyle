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
public class InputIllegalBlockTagBlockComment {
    /* A block comment before the Javadoc. */
    // violation 4 lines below 'Block tag 'since' is matched illegal pattern'
    /**
     * Some javadoc.
     *
     * @since next
     */
    void commentBefore() {
    }

    // violation 4 lines below 'Block tag 'since' is matched illegal pattern'
    /**
     * Some javadoc.
     *
     * @since next
     */
    /* A block comment after the Javadoc. */
    void commentAfter() {
    }

    /* A block comment before the Javadoc. */
    // violation 4 lines below 'Block tag 'since' is matched illegal pattern'
    /**
     * Some javadoc.
     *
     * @since next
     */
    @Deprecated
    void commentBeforeAnnotated() {
    }

    /* A block comment before the Javadoc. */
    /**
     * Some javadoc.
     *
     * @since 2.0
     */
    void commentBeforeValid() {
    }

    /* A block comment before the Javadoc. */
    // violation 4 lines below 'Block tag 'since' is matched illegal pattern'
    /**
     * Some javadoc.
     *
     * @since next
     */
    class Nested {
    }
}
