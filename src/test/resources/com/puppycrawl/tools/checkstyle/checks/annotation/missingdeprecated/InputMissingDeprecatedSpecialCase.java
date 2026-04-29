/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

// violation 3 lines below 'Duplicate @deprecated tag.'
/**
 * @deprecated bleh
 * @deprecated boo
 */
@Deprecated
public class InputMissingDeprecatedSpecialCase
{
    // violation 3 lines below 'Duplicate @deprecated tag.'
    /**
     * @deprecated bleh
     * @deprecated boo
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

    // violation 3 lines below 'Duplicate @deprecated tag.'
    /**
     * @deprecated
     * @deprecated
     */
    @Deprecated
    public void foo3() {
    }

    // violation 3 lines below 'Duplicate @deprecated tag.'
    /**
     * @deprecated bleh
     * @deprecated
     */
    @Deprecated
    public void foo4() {
    }

    // violation 3 lines below 'Duplicate @deprecated tag.'
    /**
     * @deprecated
     * @deprecated bleh
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
