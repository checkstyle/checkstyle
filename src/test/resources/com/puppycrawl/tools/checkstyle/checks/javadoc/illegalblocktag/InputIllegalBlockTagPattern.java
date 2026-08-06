/*
IllegalBlockTag
tag = @somesince
tagTextPattern = ^[1-9\\.]+$
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, METHOD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.illegalblocktag;

/**
 * some doc
 * @somesince 1.2
 */
public class InputIllegalBlockTagPattern {
    // violation 3 lines below 'Block tag 'somesince' is matched illegal pattern'
    /**
     * some doc
     * @somesince 1.1-betta
     */
    void testMethod1() {
    }

    // violation 3 lines below 'Block tag 'somesince' is matched illegal pattern'
    /**
     * some doc
     * @somesince
     */
    void testMethod2() {
    }

    /**
     * some doc
     * @somesince 1.6
     */
    void testMethod3() {
    }
}
