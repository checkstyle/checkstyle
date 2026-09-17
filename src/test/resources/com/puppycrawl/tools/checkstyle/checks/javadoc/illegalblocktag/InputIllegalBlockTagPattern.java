/*
IllegalBlockTag
tag = @since
tagTextPattern = ^[1-9\\.]+$
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, METHOD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.illegalblocktag;

/**
 * some doc
 * @since 1.2
 */
public class InputIllegalBlockTagPattern {
    // violation 3 lines below 'Block tag 'since' is matched illegal pattern'
    /**
     * some doc
     * @since 1.1-beta
     */
    void testMethod1() {
    }

    // violation 3 lines below 'Block tag 'since' is matched illegal pattern'
    /**
     * some doc
     * @since
     */
    void testMethod2() {
    }

    /**
     * some doc
     * @since 1.6
     */
    void testMethod3() {
    }
}
