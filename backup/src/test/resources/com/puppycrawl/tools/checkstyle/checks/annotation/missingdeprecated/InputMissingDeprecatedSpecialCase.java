/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

/**
 * @deprecated bleh
 * @deprecated boo // violation 'Duplicate @deprecated tag.'
 */
@Deprecated
public class InputMissingDeprecatedSpecialCase
{
    /**
     * @deprecated bleh
     * @deprecated boo // violation 'Duplicate @deprecated tag.'
     */
    public int i; // violation 'Must include both @java.lang.Deprecated annotation.*@deprecated.*'

    /**
     * @deprecated
     */
    public void foo() { // violation 'Must.*@java.lang.Deprecated annotation.*@deprecated.*'

    }

    /**
     * @deprecated
     */
    @Deprecated
    public void foo2() {

    }

    /**
     * @deprecated
     * @deprecated // violation 'Duplicate @deprecated tag.'
     */
    @Deprecated
    public void foo3() {

    }

    /**
     * @deprecated bleh
     * @deprecated // violation 'Duplicate @deprecated tag.'
     */
    @Deprecated
    public void foo4() {

    }

    /**
     * @deprecated
     * @deprecated bleh // violation 'Duplicate @deprecated tag.'
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
    int[] dontUse3() { // violation 'Must.*@java.lang.Deprecated annotation.*@deprecated.*'
        return null;
    }

    /**
     * @deprecated
     */
    <T> T dontUse4() { // violation 'Must.*@java.lang.Deprecated annotation.*@deprecated.*'
        return null;
    }

    /**
     * @deprecated
     */
    java.lang.String dontUse5() { // violation '.*@java.lang.Deprecated annotation.*@deprecated.*'
        return null;
    }
}
