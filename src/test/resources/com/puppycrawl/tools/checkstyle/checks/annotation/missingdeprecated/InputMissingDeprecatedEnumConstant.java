/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

public enum InputMissingDeprecatedEnumConstant {

    /**
     * @deprecated use CURRENT instead
     */
    @Deprecated
    LEGACY,

    /** @deprecated use LEGACY instead */
    CURRENT; // violation 'Must include.*@java.lang.Deprecated.*'
}
