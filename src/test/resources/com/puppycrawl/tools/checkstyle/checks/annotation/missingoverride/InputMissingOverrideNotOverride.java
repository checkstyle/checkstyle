package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

/* Config:
 * javaFiveCompatibility = "false"
 */
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
