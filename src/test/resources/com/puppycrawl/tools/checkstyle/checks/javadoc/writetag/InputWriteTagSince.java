/*
WriteTag
tag = @since
tagFormat = (default)null
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, METHOD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * Some class
 *
 */
public class InputWriteTagSince {
    // violation 1 lines above 'Javadoc comment is missing @since tag.'
    /**
     * some doc
     * @since
     */
    void testMethod1() {}

    /**
     * some doc
     * @since 1.6
     */
    void testMethod1WithNumSince() {}

    /**
     * some doc
     * @since 1.1-beta
     */
    void testMethod1WithAlphaSince() {}

    /** some doc */
    public void testMethod2() {}
    // violation 1 lines above 'Javadoc comment is missing @since tag.'
}
