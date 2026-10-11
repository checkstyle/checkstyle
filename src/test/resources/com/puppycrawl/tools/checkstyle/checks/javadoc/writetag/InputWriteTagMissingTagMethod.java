/*
WriteTag
tag = @since
tagFormat = (default)null
tokens = METHOD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

class InputWriteTagMissingTagMethod {
    // violation 2 lines below 'Javadoc comment is missing @since tag.'
    /** some doc */
    void methodWithoutSince() {
    }

    /**
     * some doc
     * @since 1.0
     */
    void methodWithSince() {
    }
}
