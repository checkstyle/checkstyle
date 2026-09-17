/*
WriteTag
tag = @since
tagFormat = (default)null
tokens = ENUM_CONSTANT_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

public enum InputWriteTagEnum {
    /**
     * doc for FIRST
     */
    FIRST, // violation 'Javadoc comment is missing @since tag.'

    SECOND,

    /**
     * doc for THIRD
     */
    @Deprecated // violation 'Javadoc comment is missing @since tag.'
    THIRD
}
