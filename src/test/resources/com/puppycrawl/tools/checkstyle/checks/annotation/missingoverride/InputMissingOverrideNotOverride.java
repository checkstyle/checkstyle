/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

public class InputMissingOverrideNotOverride
{
    /**
     * {@inheritDoc}
     */
    private void bleh() {}      // violation

    /**
     * {@inheritDoc}
     */
    public static void eh() {}      // violation

    /**
     * {@inheritDoc}
     */
    public String junk = "";        // ok

    void dodoo() {}
}
