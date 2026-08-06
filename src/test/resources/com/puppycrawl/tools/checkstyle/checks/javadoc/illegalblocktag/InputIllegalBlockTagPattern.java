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
    /**
     * some doc
     * @somesince 1.1-betta
     */
    // violation 2 lines above 'Block tag 'somesince' is matched illegal pattern'
    void testMethod1() {
    }

    /**
     * some doc
     * @somesince
     */
    // violation 2 lines above 'Block tag 'somesince' is matched illegal pattern'
    void testMethod2() {
    }

    /**
     * some doc
     * @somesince 1.6
     */
    void testMethod3() {
    }
}
