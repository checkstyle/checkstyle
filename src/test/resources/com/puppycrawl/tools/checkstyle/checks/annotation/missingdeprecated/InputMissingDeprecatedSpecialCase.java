/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

/**
 * @deprecated bleh
 * @deprecated boo // violation
 */
@Deprecated
public class InputMissingDeprecatedSpecialCase
{
    /**
     * @deprecated bleh
     * @deprecated boo // violation
     */
    public int i; // violation

    /**
     * @deprecated
     */
    public void foo() { // violation

    }

    /**
     * @deprecated
     */
    @Deprecated
    public void foo2() {

    }

    /**
     * @deprecated
     * @deprecated // violation
     */
    @Deprecated
    public void foo3() {

    }

    /**
     * @deprecated bleh
     * @deprecated // violation
     */
    @Deprecated
    public void foo4() {

    }

    /**
     * @deprecated
     * @deprecated bleh // violation
     */
    @Deprecated
    public void foo5() {

    }

    void local(@Deprecated String s) {

    }

    void local2(
        /** @deprecated bleh*/
        String s) {

    }

    void local3(/** @deprecated */ @Deprecated String s) {

    }

    /**
     * @Deprecated
     */
    void dontUse() {

    }

    /**
     * @Deprecated
     * @deprecated
     *  because I said.
     */
    @Deprecated
    void dontUse2() {

    }

    /**
     * @deprecated
     */
    int[] dontUse3() { // violation
        return null;
    }

    /**
     * @deprecated
     */
    <T> T dontUse4() { // violation
        return null;
    }

    /**
     * @deprecated
     */
    java.lang.String dontUse5() { // violation
        return null;
    }
}
